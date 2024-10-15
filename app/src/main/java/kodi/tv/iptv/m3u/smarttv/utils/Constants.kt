package kodi.tv.iptv.m3u.smarttv.utils

import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object Constants {
    const val PLAYER_CONTROLS_VISIBILITY = 5 * 1000L //5 seconds
    const val GOOGLE_PLAY_URL: String= " https://play.google.com/store/apps/details?id="



    fun hideSystemUI(activity: Activity) {
        // Configure the behavior of the hidden system bars
        val windowInsetsController =
            WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
}