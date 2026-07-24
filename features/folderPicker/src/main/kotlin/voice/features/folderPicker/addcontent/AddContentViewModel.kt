package voice.features.folderPicker.addcontent

import android.net.Uri
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import voice.core.data.folders.AudiobookFolders
import voice.core.data.folders.FolderType
import voice.navigation.Destination
import voice.navigation.Destination.OnboardingCompletion
import voice.navigation.Navigator
import voice.navigation.Origin

@AssistedInject
class AddContentViewModel(
  private val audiobookFolders: AudiobookFolders,
  private val navigator: Navigator,
  @Assisted
  private val origin: Origin,
) {

  internal fun add(uri: Uri) {
    audiobookFolders.add(
      uri = uri,
      type = FolderType.Folder,
    )
    when (origin) {
      Origin.Default -> {
        navigator.setRoot(Destination.BookOverview)
      }
      Origin.Onboarding -> {
        navigator.goTo(OnboardingCompletion)
      }
    }
  }

  internal fun back() {
    navigator.goBack()
  }

  @AssistedFactory
  interface Factory {
    fun create(origin: Origin): AddContentViewModel
  }
}
