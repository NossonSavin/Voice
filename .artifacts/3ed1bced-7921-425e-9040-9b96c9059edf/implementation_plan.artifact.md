# Show remaining book time adjusted by playback speed

This plan introduces a display for the total remaining time of the book on the playback screen. The remaining time will be calculated based on the current playback speed (e.g., if 10 hours of audio remain and speed is 2x, it will show 5 hours remaining).

## Proposed Changes

### [Playback Screen]

#### [MODIFY] [strings.xml](file:///C:/Users/Home/Desktop/Voice/core/strings/src/main/res/values/strings.xml)
- Add `<string name="playback.book_remaining">Remaining: %s</string>`.

#### [MODIFY] [BookPlayViewState.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewState.kt)
- Add `bookRemainingTime: String?` to the `BookPlayViewState` data class.
- Add `playbackSpeed: Float` to `BookPlayViewState`.

#### [MODIFY] [BookPlayViewModel.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/BookPlayViewModel.kt)
- In `viewState()`, calculate total remaining time: `(book.duration - book.position)`.
- Adjust it by speed: `(remainingTime / book.content.playbackSpeed).toLong()`.
- Format it using `formatTime` and wrap in the new string resource.
- Update `kioskModeViewState()` with demo data.

#### [MODIFY] [SliderRow.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/SliderRow.kt)
- Add `bookRemainingTime: String?` as a parameter to `SliderRow`.
- Wrap the existing `Row` in a `Column`.
- Add a small `Text` below the slider showing `bookRemainingTime`.

#### [MODIFY] [BookPlayContent.kt](file:///C:/Users/Home/Desktop/Voice/features/playbackScreen/src/main/kotlin/voice/features/playbackScreen/view/BookPlayContent.kt)
- Pass `viewState.bookRemainingTime` to `SliderRow`.

## Verification Plan

### Manual Verification
1. Open the playback screen for a book.
2. Observe the "Remaining" time for the book below the slider.
3. Change the playback speed (e.g., to 2.0x).
4. Verify that the remaining time updates to be half of what it was at 1.0x.
5. Verify that seeking within a chapter updates the remaining time correctly.
