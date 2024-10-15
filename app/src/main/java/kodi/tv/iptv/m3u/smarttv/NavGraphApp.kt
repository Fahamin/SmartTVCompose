package kodi.tv.iptv.m3u.smarttv

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.screen.ChannelDetailScreen
import kodi.tv.iptv.m3u.smarttv.screen.MainScreen
import kodi.tv.iptv.m3u.smarttv.screen.OnboardingScreen
import kodi.tv.iptv.m3u.smarttv.viewModel.DbViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.PlayerViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.SearchChannelViewModel

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavGraphApp(
    viewModel: DbViewModel,
    playerViewModel: PlayerViewModel,
    searchChannelViewModel: SearchChannelViewModel,

    navController: NavHostController,
    context: ComponentActivity
) {
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
            MainScreen(viewModel, playerViewModel,searchChannelViewModel, context, navController)
        }

        composable(Routes.DetailScreen) {
            ChannelDetailScreen(viewModel, playerViewModel,searchChannelViewModel, context, navController)
        }

    }
}