package voice.app

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationAttributes
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Inject
import voice.core.common.rootGraphAs
import voice.core.playback.PlayerController

@ContributesTo(AppScope::class)
interface SilentPlaybackActivityGraph {
  fun inject(activity: SilentPlaybackActivity)
}

class SilentPlaybackActivity : Activity() {

  @Inject
  private lateinit var playerController: PlayerController

  override fun onCreate(savedInstanceState: Bundle?) {
    rootGraphAs<SilentPlaybackActivityGraph>().inject(this)
    super.onCreate(savedInstanceState)

    vibrateHardHaptic()
    playerController.playPause()
    Handler(Looper.getMainLooper()).postDelayed({
      finish()
      moveTaskToBack(true)
      @Suppress("DEPRECATION")
      overridePendingTransition(0, 0)
    }, 100)
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
