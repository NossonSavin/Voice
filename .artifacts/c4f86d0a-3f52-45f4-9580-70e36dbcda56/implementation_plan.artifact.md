# Implementation Plan - Update "Play Current" Shortcut Icon

The user wants the "Play Current" shortcut to use the same icon as the main app icon.

## User Review Required

> [!IMPORTANT]
> I will update the icon for the `SilentPlaybackActivity` (which handles "Play Current") and its corresponding launcher shortcut to use `@mipmap/ic_launcher`. This will make the shortcut icon identical to the main app icon.

## Proposed Changes

### App Manifest

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/Home/Desktop/Voice/app/src/main/AndroidManifest.xml)
- Change `android:icon` for `.SilentPlaybackActivity` from `@drawable/ic_shortcut_play` to `@mipmap/ic_launcher`.

### Shortcut Configuration

#### [MODIFY] [shortcuts.xml](file:///C:/Users/Home/Desktop/Voice/app/src/main/res/xml/shortcuts.xml)
- Change `android:icon` for the `playCurrent` shortcut from `@drawable/ic_shortcut_play` to `@mipmap/ic_launcher`.

## Verification Plan

### Manual Verification
- Re-deploy the app.
- Check the "Play Current" shortcut (long-press on the app icon) and verify it now uses the new app icon.
- Verify the `SilentPlaybackActivity` icon if visible in system settings or recents.
