package kodi.tv.iptv.m3u.smarttv.player

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LiveData
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import androidx.media3.ui.PlayerView
import com.hulu.peacoke.peacoketv.ui.common_component.Loader
import kodi.tv.iptv.m3u.smarttv.ui.theme.darkBackground
import com.peacoke.peacoketv.utils.setPortrait

import kodi.tv.iptv.m3u.smarttv.model.ChannelModel


@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    videoUrl: LiveData<ChannelModel>,
    isFullScreen: Boolean,
    navigateBack: (() -> Unit)? = null,
    onPlayerClick: () -> Unit = {}

) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    DisposableEffect(Unit) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
    BackHandler {
        if (isFullScreen)
            context.setPortrait()
        else
            navigateBack?.invoke()

    }
    val loading = remember {
        mutableStateOf(true)
    }

    val isPlayFinished = remember {
        mutableStateOf(false)
    }

    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }


    AndroidView(
        modifier = Modifier.clickable { onPlayerClick() },
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                useController = false
                RESIZE_MODE_FILL
            }
        },
    )

    LaunchedEffect(videoUrl.observeAsState().value) {
        if (videoUrl.value != null) {
            val defaultDataSourceFactory = DefaultDataSource.Factory(context)
            val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                context,
                defaultDataSourceFactory
            )
            val source = if (videoUrl.value!!.path!!.contains("m3u8"))
                getHlsMediaSource(dataSourceFactory, videoUrl.value!!.path!!)
            else
                getProgressiveMediaSource(dataSourceFactory, videoUrl.value!!.path!!)

            exoPlayer.setMediaSource(source)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    Box(
        modifier = Modifier
            .background(darkBackground)
    ) {

        DisposableEffect(key1 = Unit) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        exoPlayer.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        exoPlayer.play()
                    }

                    else -> {
                        exoPlayer.pause()
                    }
                }
            }
            val lifecycle = lifecycleOwner.value.lifecycle
            lifecycle.addObserver(observer)
            onDispose {
                exoPlayer.stop()
                exoPlayer.release()
                lifecycle.removeObserver(observer)
            }

        }

        if (loading.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Loader()
            }
        }
    }

    exoPlayer.addListener(object : Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                ExoPlayer.STATE_ENDED -> {
                    exoPlayer.stop()
                    exoPlayer.release()
                    Log.e("player_loading", " state Finished")
                    isPlayFinished.value = true
                }

                ExoPlayer.STATE_READY -> {
                    loading.value = false
                }

                ExoPlayer.STATE_BUFFERING -> {
                    loading.value = true

                }

                else -> {
                    loading.value = false
                }
            }
        }

    })

}


@OptIn(UnstableApi::class)
private fun getHlsMediaSource(
    dataSourceFactory: DataSource.Factory,
    mediaUrl: String
): MediaSource {
    // Create a HLS media source pointing to a playlist uri.
    return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(mediaUrl))
}

@UnstableApi
private fun getProgressiveMediaSource(
    dataSourceFactory: DataSource.Factory,
    mediaUrl: String
): MediaSource {
    // Create a Regular media source pointing to a playlist uri.
    return ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(MediaItem.fromUri(Uri.parse(mediaUrl)))
}