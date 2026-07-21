# Walkthrough - Global option to hide book cover from system surfaces

I have moved the "Hide cover from system" option to the main Preferences screen, making it a global app-wide setting that hides book covers from notifications, lock screens, and widgets while keeping them visible within the app.

## Changes Made

### Main Settings (Global)
- **Strings**: Added `settings_playback_hide_cover_from_system_title` and `settings_playback_hide_cover_from_system_summary` to `strings.xml`.
- **Settings Screen**:
    - Added a new toggle in **Preferences** under the Playback section.
    - Updated `SettingsViewModel` to handle the global state and immediately refresh the active media session when toggled.
- **DataStore**: The setting is now stored globally in `HideCoverFromSystemStore`.

### System-Wide Integration
- **Media Session (Notifications/Lock Screen)**: Updated `MediaItemProvider` to check the global setting. When enabled, it intentionally omits the `artworkUri` from the media metadata.
- **Instant Update**: `PlayerController.refreshMediaItem()` now forces the media controller to update its current item when the setting is changed, ensuring the notification artwork disappears or reappears instantly without stopping playback.
- **Widgets**: Updated `TriggerWidgetOnChange` to listen for this setting change and `WidgetUpdater` to respect it, ensuring your home screen widgets also follow your privacy preference.

### Playback Screen Cleanup
- Removed the per-book "Hide cover from system" toggle from the playback screen's overflow menu and view models to avoid confusion and maintain a clean UI.

## Verification Results

### Automated Tests
- Ran `./gradlew :app:assembleFreeDebug` and the build finished successfully.

### Manual Verification Suggestion
1. Go to **Preferences** (tap the settings icon in the top right of the main screen).
2. Find and toggle **"Hide cover from system"**.
3. Check your notification shade and lock screen while playing a book:
    - If enabled, the cover should be replaced by a default icon or removed entirely.
    - If disabled, the book cover should return.
4. Verify your Home Screen widget also respects this toggle.
5. Confirm the cover is still fully visible on the in-app playback screen.
