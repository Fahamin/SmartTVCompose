package kodi.tv.iptv.m3u.smarttv.botom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kodi.tv.iptv.m3u.smarttv.route.Routes


sealed class BottomBarNavigation(
    var route: String,
    var title: String,
    var icon: ImageVector
) {
    object Home : BottomBarNavigation(
        route = Routes.home,
        title = "HOME",
        icon = Icons.Default.Home
    )

    object m3u : BottomBarNavigation(
        route = Routes.m3u,
        title = "M3U",
        icon = Icons.Default.Settings
    )

    object channel : BottomBarNavigation(
        route = Routes.channel,
        title = "Channel",
        icon = Icons.Default.Settings
    )

    object saveChannel : BottomBarNavigation(
        route = Routes.saveChannel,
        title = "Favorite",
        icon = Icons.Default.Settings
    )
}