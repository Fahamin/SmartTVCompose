package kodi.tv.iptv.m3u.smarttv

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kodi.tv.iptv.m3u.smarttv.repository.DbRepository
import kodi.tv.iptv.m3u.smarttv.ui.theme.SmartTVComposeTheme
import kodi.tv.iptv.m3u.smarttv.viewModel.DbViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.PlayerViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.SearchChannelViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
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
                    val viewModel = hiltViewModel<DbViewModel>()
                    val playerViewModel = hiltViewModel<PlayerViewModel>()
                    val seraViewModel = hiltViewModel<SearchChannelViewModel>()
                    val navController = rememberNavController()
                    val context = this@MainActivity
                    NavGraphApp(viewModel, playerViewModel, seraViewModel, navController, context)

                }
            }
        }
    }
}
