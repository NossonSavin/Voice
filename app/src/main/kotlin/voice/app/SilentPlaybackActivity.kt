package voice.app

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationAttributes
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.KeyEvent

class SilentPlaybackActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    dispatchPlayPauseKeyEvent()
    vibrateHardHaptic()
    Handler(Looper.getMainLooper()).postDelayed({
      finish()
      @Suppress("DEPRECATION")
      overridePendingTransition(0, 0)
    }, 100)
  }

  private fun dispatchPlayPauseKeyEvent() {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return
    audioManager.dispatchMediaKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE))
    audioManager.dispatchMediaKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE))
  }

  private fun vibrateHardHaptic() {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vibratorManager = getSystemService(VibratorManager::class.java)
      vibratorManager?.defaultVibrator
    } else {
      getSystemService(Vibrator::class.java)
    } ?: return

    if (!vibrator.hasVibrator()) return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val effect = VibrationEffect.createOneShot(150, 255)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        vibrator.vibrate(effect, VibrationAttributes.createForUsage(VibrationAttributes.USAGE_ALARM))
      } else {
        vibrator.vibrate(effect)
      }
    } else {
      @Suppress("DEPRECATION")
      vibrator.vibrate(150)
    }
  }
}
