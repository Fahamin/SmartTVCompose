package kodi.tv.iptv.m3u.smarttv

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.screen.ChannelScreen
import kodi.tv.iptv.m3u.smarttv.screen.HomeScreen
import kodi.tv.iptv.m3u.smarttv.screen.M3uScreen
import kodi.tv.iptv.m3u.smarttv.screen.PlaylistScreen
import kodi.tv.iptv.m3u.smarttv.screen.SaveChannelScreen
import org.checkerframework.checker.units.qual.s

@Composable
fun BottomNavigateScreen(context: Context, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.bottom
    ) {
        navigation(
            startDestination = Routes.home,
            route = Routes.bottom
        ) {
            composable(route = Routes.home)
            {
                HomeScreen(navController)
            }
            composable(route = Routes.m3u)
            {
                M3uScreen(navController)
            }
            composable(route = Routes.channel)
            {
                ChannelScreen(navController)
            }
            composable(route = Routes.saveChannel)
            {
                SaveChannelScreen(navController)
            }
        }


        navigation(
            startDestination = Routes.playlist,
            route = Routes.play
        ){
            composable(route = Routes.playlist)
            {
                PlaylistScreen(navController)
            }
        }
    }
}