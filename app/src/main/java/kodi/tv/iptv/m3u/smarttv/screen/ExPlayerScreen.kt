package kodi.tv.iptv.m3u.smarttv.screen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import kodi.tv.iptv.m3u.smarttv.R

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    navController: NavHostController,
    link: String?,
    cat: String?,
    activity: ComponentActivity
) {
    Log.e("fahamin", cat!!)
    val context = LocalContext.current
    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
    val hlsMediaSource =
        HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(link.toString()))

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .apply {
                setSeekBackIncrementMs(PLAYER_SEEK_BACK_INCREMENT)
                setSeekForwardIncrementMs(PLAYER_SEEK_FORWARD_INCREMENT)
            }
            .build()
            .apply {
                setMediaSource(
                    hlsMediaSource
                )
                prepare()
                playWhenReady = true
            }
    }

    var shouldShowControls by remember { mutableStateOf(false) }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    var isFullScreen by remember { mutableStateOf(false) }

    var playerModifier = if (isFullScreen) {
        Modifier.fillMaxSize()
    } else {
        Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    }

    Column {
        Box(modifier = Modifier) {
            DisposableEffect(key1 = Unit) {
                val listener =
                    object : Player.Listener {
                        override fun onEvents(
                            player: Player,
                            events: Player.Events
                        ) {
                            super.onEvents(player, events)
                            totalDuration = player.duration.coerceAtLeast(0L)
                            currentTime = player.currentPosition.coerceAtLeast(0L)
                            bufferedPercentage = player.bufferedPercentage
                            isPlaying = player.isPlaying
                            playbackState = player.playbackState
                        }
                    }

                exoPlayer.addListener(listener)

                onDispose {
                    exoPlayer.removeListener(listener)
                    exoPlayer.release()
                }
            }

            AndroidView(
                modifier =
                playerModifier.clickable {
                    shouldShowControls = shouldShowControls.not()
                },
                factory = {

                    PlayerView(context).also { playerView ->
                        playerView.player = exoPlayer
                        playerView.useController = false
                        playerView.keepScreenOn = true


                    }
                }
            )


            PlayerControls(
                isVisible = { shouldShowControls },
                isPlaying = { isPlaying },
                title = { exoPlayer.mediaMetadata.displayTitle.toString() },
                playbackState = { playbackState },
                onReplayClick = { exoPlayer.seekBack() },
                onForwardClick = { exoPlayer.seekForward() },
                onPauseToggle = {
                    when {
                        exoPlayer.isPlaying -> {
                            // pause the video
                            exoPlayer.pause()
                        }

                        exoPlayer.isPlaying.not() &&
                                playbackState == Player.STATE_ENDED -> {
                            exoPlayer.seekTo(0)
                            exoPlayer.playWhenReady = true
                        }

                        else -> {
                            // play the video
                            // it's already paused
                            exoPlayer.play()
                        }
                    }
                    isPlaying = isPlaying.not()
                },
                totalDuration = { totalDuration },
                currentTime = { currentTime },
                bufferedPercentage = { bufferedPercentage },
                onSeekChanged = { timeMs: Float ->
                    exoPlayer.seekTo(timeMs.toLong())
                },
                activity = activity
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {

        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PlayerControls(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    title: () -> String,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPauseToggle: () -> Unit,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    bufferedPercentage: () -> Int,
    playbackState: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    activity: ComponentActivity
) {

    val visible = remember(isVisible()) { isVisible() }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        Box(
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {

            val view = LocalView.current

            val buffer = remember(bufferedPercentage()) { bufferedPercentage() }
            val context = LocalContext.current
            val currentOrientation =
                remember { mutableStateOf(context.resources.configuration.orientation) }

            var showSystemUi by remember { mutableStateOf(false) }
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            if (!view.isInEditMode) {
                if (!showSystemUi) {
                    insetsController.apply {
                        hide(WindowInsetsCompat.Type.systemBars())
                        systemBarsBehavior =
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                } else {
                    insetsController.apply { show(WindowInsetsCompat.Type.systemBars()) }
                }
            }


            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                onClick = {
                    if (currentOrientation.value == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                        activity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        currentOrientation.value = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        showSystemUi = true

                    } else {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        currentOrientation.value = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        showSystemUi = false


                    }

                }
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomEnd),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.baseline_fullscreen_24),
                    contentDescription = "Enter/Exit fullscreen"
                )
            }
        }

    }
}

private const val PLAYER_SEEK_BACK_INCREMENT = 5 * 1000L // 5 seconds
private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L // 10 seconds