package voice.core.scanner

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import voice.core.data.BookId
import voice.core.data.audioFileCount
import voice.core.data.isAudioFile
import voice.core.data.repo.BookContentRepo
import voice.core.documentfile.CachedDocumentFile
import voice.core.documentfile.walk
import voice.core.logging.api.Logger

@Inject
internal class MediaScanner(
  private val contentRepo: BookContentRepo,
  private val chapterParser: ChapterParser,
  private val bookParser: BookParser,
  private val deviceHasPermissionBug: DeviceHasStoragePermissionBug,
) {

  suspend fun performScan(folders: List<CachedDocumentFile>) {
    val files = folders.flatMap { findBookFolders(it) }.distinctBy { it.uri }

    contentRepo.setAllInactiveExcept(files.map { BookId(it.uri) })

    val probeFile = files.findProbeFile()
    if (probeFile != null) {
      if (deviceHasPermissionBug.checkForBugAndSet(probeFile)) {
        Logger.w("Device has permission bug, aborting scan! Probed $probeFile")
        return
      }
    }

    val semaphore = Semaphore(4)
    coroutineScope {
      files
        .sortedByDescending { it.audioFileCount() }
        .map { file ->
          async {
            semaphore.withPermit {
              scan(file)
            }
          }
        }
        .awaitAll()
    }
  }

  private fun findBookFolders(file: CachedDocumentFile): List<CachedDocumentFile> {
    if (file.isFile) return listOf(file)
    val subFolders = file.children.filter { it.isDirectory }
    return if (subFolders.isEmpty()) {
      listOf(file)
    } else {
      subFolders.flatMap { findBookFolders(it) }
    }
  }

  private fun List<CachedDocumentFile>.findProbeFile(): CachedDocumentFile? {
    return asSequence().flatMap { it.walk() }
      .firstOrNull { child ->
        child.isAudioFile() && child.uri.authority == "com.android.externalstorage.documents"
      }
  }

  private suspend fun scan(file: CachedDocumentFile) {
    val parseResult = chapterParser.parse(file)
    val chapters = parseResult.chapters
    if (chapters.isEmpty()) return

    val content = bookParser.parseAndStore(chapters, file, parseResult.firstChapterMetadata)

    val chapterIds = chapters.map { it.id }
    val currentChapterGone = content.currentChapter !in chapterIds
    val currentChapter = if (currentChapterGone) chapterIds.first() else content.currentChapter
    val positionInChapter = if (currentChapterGone) 0 else content.positionInChapter
    val updated = content.copy(
      chapters = chapterIds,
      currentChapter = currentChapter,
      positionInChapter = positionInChapter,
      isActive = true,
    )
    if (content != updated) {
      validateIntegrity(updated, chapters)
      contentRepo.put(updated)
    }
  }
}
