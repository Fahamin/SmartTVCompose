package kodi.tv.iptv.m3u.smarttv

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.screen.ChannelScreen
import kodi.tv.iptv.m3u.smarttv.screen.HomeScreen
import kodi.tv.iptv.m3u.smarttv.screen.M3uScreen
import kodi.tv.iptv.m3u.smarttv.screen.MainScreen
import kodi.tv.iptv.m3u.smarttv.screen.OnboardingScreen
import kodi.tv.iptv.m3u.smarttv.screen.PlayerScreen
import kodi.tv.iptv.m3u.smarttv.screen.PlaylistScreen
import kodi.tv.iptv.m3u.smarttv.screen.SaveChannelScreen

@Composable
fun NavGraphApp(navController: NavHostController, context: Context) {
    NavHost(
        navController = navController,
        startDestination = Routes.onBoard,
        route = "main"
    ) {
        composable(Routes.onBoard) {
            OnboardingScreen(
                navController = navController,
                context = context
            )
        }
        composable(Routes.bottom) {
            MainScreen(navController)
        }

        composable(
            route = "${Routes.player1}?name={name}",
            arguments = listOf(
                navArgument(name = "name"){
                    type = NavType.StringType
                    //defaultValue= "user"
                    nullable = true
                }
            )
        ){ backstackEntry ->
            PlayerScreen(navController,
                link = backstackEntry.arguments?.getString("name")
            )
        }

        composable(route = Routes.playlist,
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("link") {
                    type = NavType.StringType
                }
            ))
        {
            val name = it.arguments?.getString("name") ?: ""
            val link = it.arguments?.getString("link") ?: ""

            PlaylistScreen(navController, name, link)
        }

    }
}