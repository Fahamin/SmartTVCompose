package kodi.tv.iptv.m3u.smarttv

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.screen.ChannelScreen
import kodi.tv.iptv.m3u.smarttv.screen.HomeScreen
import kodi.tv.iptv.m3u.smarttv.screen.M3uScreen
import kodi.tv.iptv.m3u.smarttv.screen.SaveChannelScreen

@Composable
fun BottomNavigateScreen(context: Context, navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.home
    ) {
        composable(route = Routes.home)
        {
            HomeScreen()
        }
        composable(route = Routes.m3u)
        {
            M3uScreen()
        }
        composable(route = Routes.channel)
        {
            ChannelScreen()
        }
        composable(route = Routes.saveChannel)
        {
            SaveChannelScreen()
        }
    }
}