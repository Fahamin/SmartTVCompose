package kodi.tv.iptv.m3u.smarttv.screen

import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
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

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(navController: NavHostController, link: String?) {

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
            Modifier.fillMaxSize()
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
                    playerView.layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
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