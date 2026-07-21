package voice.core.playback.session

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.ClippingConfiguration
import androidx.media3.common.MediaMetadata
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

internal enum class MediaType {
  AudioBook,
  AudioBookChapter,
  AudioBookRoot,
}

internal fun MediaItem(
  title: String,
  mediaId: MediaId,
  isPlayable: Boolean,
  browsable: Boolean,
  album: String? = null,
  artist: String? = null,
  genre: String? = null,
  sourceUri: Uri? = null,
  imageUri: Uri? = null,
  durationMs: Long? = null,
  clippingConfiguration: ClippingConfiguration = ClippingConfiguration.UNSET,
  mediaType: MediaType,
): MediaItem {
  val metadataBuilder =
    MediaMetadata.Builder()
      .setAlbumTitle(album)
      .setTitle(title)
      .setArtist(artist)
      .setGenre(genre)
      .setIsBrowsable(browsable)
      .setIsPlayable(isPlayable)
      .setDurationMs(durationMs)
      .setMediaType(
        when (mediaType) {
          MediaType.AudioBook -> MediaMetadata.MEDIA_TYPE_AUDIO_BOOK
          MediaType.AudioBookChapter -> MediaMetadata.MEDIA_TYPE_AUDIO_BOOK_CHAPTER
          MediaType.AudioBookRoot -> MediaMetadata.MEDIA_TYPE_FOLDER_AUDIO_BOOKS
        },
      )

  if (imageUri != null) {
    metadataBuilder.setArtworkUri(imageUri)
  } else {
    // Setting artwork data to an empty byte array can help force hiding the artwork on some systems
    metadataBuilder.setArtworkData(byteArrayOf(), MediaMetadata.PICTURE_TYPE_FRONT_COVER)
  }

  val metadata = metadataBuilder.build()

  return MediaItem.Builder()
    .setMediaId(Json.encodeToString(MediaId.serializer(), mediaId))
    .setMediaMetadata(metadata)
    .setUri(sourceUri)
    .setClippingConfiguration(clippingConfiguration)
    .build()
}

fun String.toMediaIdOrNull(): MediaId? = try {
  Json.decodeFromString(MediaId.serializer(), this)
} catch (_: SerializationException) {
  null
}
