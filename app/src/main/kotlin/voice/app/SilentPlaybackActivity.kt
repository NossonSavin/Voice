package voice.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Inject
import voice.core.common.rootGraphAs
import voice.core.playback.PlayerController

@ContributesTo(AppScope::class)
interface SilentPlaybackActivityGraph {
  fun inject(activity: SilentPlaybackActivity)
}

class SilentPlaybackActivity : AppCompatActivity() {

  @Inject
  private lateinit var playerController: PlayerController

  override fun onCreate(savedInstanceState: Bundle?) {
    rootGraphAs<SilentPlaybackActivityGraph>().inject(this)
    super.onCreate(savedInstanceState)

    playerController.playPause()
    finish()
  }
}
