package kodi.tv.iptv.m3u.smarttv.screen

import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    navController: NavHostController,
    link: String?,
    activitycompose: ComponentActivity
) {

    Log.e("fahamin", link.toString())

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current
    var isFullScreen by remember { mutableStateOf(false) }

    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
    val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
        .createMediaSource(MediaItem.fromUri(link.toString()))

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaSource(hlsMediaSource)
            prepare()
            playWhenReady = true
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column {
        val playerModifier = if (isFullScreen) {
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        } else {
            Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        }

        AndroidView(
            modifier = playerModifier,
            factory = {
                PlayerView(context).also { playerView ->
                    playerView.player = exoPlayer
                    playerView.useController = false
                    playerView.keepScreenOn = true
                    playerView.setFullscreenButtonClickListener {
                        if (isFullScreen)
                             playerView.layoutParams = FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )

                    }

                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()

                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()

                    }

                    else -> Unit
                }
            }
        )

        Button(
            onClick = { isFullScreen = !isFullScreen },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(if (isFullScreen) "Exit Full Screen" else "Full Screen")
        }
    }
}

@OptIn(UnstableApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun HandleFullScreen(activity: ComponentActivity, exoPlayer: ExoPlayer) {
    val controller = activity.window.insetsController
    if (controller != null) {
        controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
    } else {
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    exoPlayer.playWhenReady = true
    exoPlayer.playbackState
}