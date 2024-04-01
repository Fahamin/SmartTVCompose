package kodi.tv.iptv.m3u.smarttv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.screen.MainScreen
import kodi.tv.iptv.m3u.smarttv.screen.OnboardingScreen
import kodi.tv.iptv.m3u.smarttv.ui.theme.SmartTVComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SmartTVComposeTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Routes.onBoard) {

                        composable(Routes.onBoard) {
                            OnboardingScreen(
                                navController = navController,
                                context = this@MainActivity
                            )
                        }
                        composable(Routes.mainScreen) {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}
