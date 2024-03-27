package kodi.tv.iptv.m3u.smarttv

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.securevpn.zoovpn.globalvpn.route.Routes

@Composable
fun NavigateScreen(context: Context, navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.home
    ) {
        composable(route = Routes.home)
        {
            OnboardingScreen(navHostController, context)
        }
        composable(route = Routes.server)
        {
           // ServerScreen(viewModel, navHostController)
        }
    }
}