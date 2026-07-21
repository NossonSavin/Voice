# Enhance book progress display on playback screen

The user wants to expand the book remaining time display to show more context: total time read, total book duration, percentage completed, and the speed-adjusted remaining time. The styling should also be consistent with the chapter-level time labels.

## Proposed Changes

### [Strings]

#### [MODIFY] [strings.xml](file:///C:/Users/Home/Desktop/Voice/core/strings/src/main/res/values/strings.xml)
- Replace `playback_book_remaining` with a more comprehensive `playback_book_status` string.
- Pattern: `Read %1$s of %2$s    %3$d%%    Left %4$s`

### [Playback Screen]

#### [MODIFY] [BookPlayViewState.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewState.kt)
- Add `bookTotalDuration: Duration?`, `bookTotalPlayedTime: Duration?`, and `bookProgress: Float?` to `BookPlayViewState`.
- (Optional) Keep `bookRemainingTime` as is.

#### [MODIFY] [BookPlayViewModel.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewModel.kt)
- Populate the new fields in `viewState()` and `kioskModeViewState()`.
- `bookTotalDuration = book.duration.milliseconds`
- `bookTotalPlayedTime = book.position.milliseconds`
- `bookProgress = book.position.toFloat() / book.duration.toFloat()`

#### [MODIFY] [SliderRow.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/SliderRow.kt)
- Update `SliderRow` to accept the new progress data.
- Update the UI to use the new `playback_book_status` string.
- Remove explicit `labelSmall` styling to match the chapter time labels.

#### [MODIFY] [BookPlayView.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/BookPlayView.kt)
- Update previews to include the new data.

## Verification Plan

### Manual Verification
1. Open the playback screen.
2. Verify the status line below the slider matches the requested format: "Read 1:20:00 of 10:00:00    13%    Left 4:20:00".
3. Verify that changing playback speed updates the "Left" part correctly.
4. Verify that the font size and color match the chapter start/end times.
