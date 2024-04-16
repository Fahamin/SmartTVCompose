package kodi.tv.iptv.m3u.smarttv

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.screen.MainScreen
import kodi.tv.iptv.m3u.smarttv.screen.OnboardingScreen
import kodi.tv.iptv.m3u.smarttv.screen.PlaylistScreen
import kodi.tv.iptv.m3u.smarttv.screen.VideoPlayer

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavGraphApp(navController: NavHostController, context: ComponentActivity) {
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
            route = "${Routes.player1}?name={name}?cat={cat}",
            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                    //defaultValue= "user"
                    nullable = true
                },
                navArgument(name = "cat") {
                    type = NavType.StringType
                    //defaultValue= "user"
                    nullable = true
                }
            )
        ) { backstackEntry ->
            VideoPlayer(
                navController,
                link = backstackEntry.arguments?.getString("name"),
                cat = backstackEntry.arguments?.getString("cat"),
                context
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