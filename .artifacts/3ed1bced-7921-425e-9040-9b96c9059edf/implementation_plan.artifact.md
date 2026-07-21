# App-wide option to hide book cover from system surfaces

The user wants the "Hide cover from system" setting to be a global preference in the main settings menu, rather than a per-book option in the playback menu. This setting should hide the cover from notifications, lock screens, and widgets app-wide.

## Proposed Changes

### [Strings]

#### [MODIFY] [strings.xml](file:///C:/Users/Home/Desktop/Voice/core/strings/src/main/res/values/strings.xml)
- Move/Rename `playback_option_hide_cover_from_system` to `settings_playback_hide_cover_from_system_title` and add a summary string `settings_playback_hide_cover_from_system_summary`.

### [Settings Screen]

#### [MODIFY] [SettingsViewState.kt](file:///C:/Users/Home/Desktop/Voice/features/settings/src/main/kotlin/voice/features/settings/SettingsViewState.kt)
- Add `hideCoverFromSystem: Boolean`.

#### [MODIFY] [SettingsViewModel.kt](file:///C:/Users/Home/Desktop/Voice/features/settings/src/main/kotlin/voice/features/settings/SettingsViewModel.kt)
- Inject `@HideCoverFromSystemStore private val hideCoverFromSystemStore: DataStore<Boolean>` and `private val player: PlayerController`.
- Expose the setting in `viewState()`.
- Implement `toggleHideCoverFromSystem()` in `SettingsListener` and `SettingsViewModel`.
- When toggled, update the store and call `player.refreshMediaItem()`.

#### [MODIFY] [Settings.kt](file:///C:/Users/Home/Desktop/Voice/features/settings/src/main/kotlin/voice/features/settings/views/Settings.kt)
- Add a new `ListItem` with a `Switch` for "Hide cover from system" in the settings list (e.g., near "Seek amount").

### [Cleanup Playback Screen]

#### [MODIFY] [BookPlayViewState.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewState.kt)
- Remove `hideCoverFromSystem`.

#### [MODIFY] [BookPlayViewModel.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewModel.kt)
- Remove injection of `hideCoverFromSystemStore` and the toggle logic.

#### [MODIFY] [OverflowMenu.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/OverflowMenu.kt)
- Remove the "Hide cover from system" menu item.

#### [MODIFY] [BookPlayAppBar.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/BookPlayAppBar.kt) & [BookPlayView.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/BookPlayView.kt)
- Remove unused parameters.

### [Widget Integration]

#### [MODIFY] [TriggerWidgetOnChange.kt](file:///C:/Users/Home/Desktop/Voice/features/widget/src/main/kotlin/voice/features/widget/TriggerWidgetOnChange.kt) (or equivalent)
- Ensure that the widget is updated whenever the `hideCoverFromSystem` setting changes.

## Verification Plan

### Manual Verification
1. Go to Preferences (Settings).
2. Toggle "Hide cover from system".
3. Verify that the notification and lock screen artwork disappear/appear immediately.
4. Verify that widgets also hide/show the cover according to the setting.
5. Verify the cover is still visible on the Playback screen in the app.
