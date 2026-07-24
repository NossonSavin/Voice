package voice.features.folderPicker

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import voice.core.data.folders.FolderType
import voice.core.ui.icons.VoiceIcons
import voice.core.strings.R as StringsR

@Composable
internal fun FolderTypeIcon(folderType: FolderType) {
  Icon(
    imageVector = folderType.icon(),
    contentDescription = folderType.contentDescription(),
  )
}

private fun FolderType.icon(): ImageVector = when (this) {
  FolderType.File -> VoiceIcons.AudioFile
  FolderType.Folder -> VoiceIcons.Folder
}

@Composable
private fun FolderType.contentDescription(): String {
  val res = when (this) {
    FolderType.File -> StringsR.string.folder_mode_single_title
    FolderType.Folder -> StringsR.string.folder_mode_root_title
  }
  return stringResource(res)
}
