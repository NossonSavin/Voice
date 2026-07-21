# Update playback speed display and add presets

This plan updates the playback screen to show the current playback speed as text instead of a generic icon and adds preset buttons to the speed selection dialog.

## Proposed Changes

### [Playback Screen]

#### [MODIFY] [BookPlayViewState.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewState.kt)
- Add `playbackSpeed: Float` to `BookPlayViewState`.

#### [MODIFY] [BookPlayViewModel.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewModel.kt)
- Pass `book.content.playbackSpeed` to `BookPlayViewState`.
- Update `kioskModeViewState()` with a default speed (1.0f).

#### [MODIFY] [BookPlayAppBar.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/BookPlayAppBar.kt)
- Replace the speed `IconButton` with a text-based display showing the current speed (e.g., "1.25x").
- Use `DecimalFormat("0.##")` to format the speed.

#### [MODIFY] [SpeedDialog.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/SpeedDialog.kt)
- Add a row of preset buttons (1x, 1.25x, 1.50x, 1.75x, 2x) below the speed slider.
- Clicking a preset updates the speed via `viewModel.onPlaybackSpeedChanged`.

## Verification Plan

### Manual Verification
1. Open the playback screen.
2. Verify that the speed icon is now text showing the current speed (e.g., "1x").
3. Click the speed text to open the dialog.
4. Verify the preset buttons (1x, 1.25x, 1.50x, 1.75x, 2x) are visible.
5. Click a preset button and verify the speed updates on both the slider and the app bar.
