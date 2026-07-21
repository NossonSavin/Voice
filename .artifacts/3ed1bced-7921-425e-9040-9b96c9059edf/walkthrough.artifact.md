# Walkthrough - Remaining Book Time Calculation

I have implemented a feature to show the total remaining time of the book on the playback screen, adjusted for the current playback speed.

## Changes Made

### Core Strings
- Added a new string resource `playback_book_remaining` in `strings.xml` to display the "Remaining: [time]" label.

### Playback Screen Feature
- **BookPlayViewState.kt**: Updated the `BookPlayViewState` data class to include `bookRemainingTime: Duration?`.
- **BookPlayViewModel.kt**:
    - Implemented logic in `viewState()` to calculate the remaining book time: `(total duration - current position) / playback speed`.
    - Updated `kioskModeViewState` with demo data for the remaining time.
- **SliderRow.kt**:
    - Modified the UI to display the book's remaining time below the seek bar.
    - Used `MaterialTheme.typography.labelSmall` for a clean, unobtrusive look.
- **BookPlayContent.kt**: Updated calls to `SliderRow` to pass the new `bookRemainingTime` value for both portrait and landscape layouts.
- **BookPlayView.kt**: Updated the `PreviewParameterProvider` to include dummy data for the new field, ensuring previews continue to work.

## Verification Results

### Automated Tests
- Ran `:features:playbackScreen:assembleDebug` and it finished successfully.
- Verified that all components are correctly wired and compile.

### Manual Verification Suggestion
- Open the playback screen.
- Verify that a "Remaining: ..." label appears below the slider.
- Change the playback speed and confirm that the remaining time is updated accordingly (e.g., at 2x speed, it should show half the duration compared to 1x).
