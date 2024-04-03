package kodi.tv.iptv.m3u.smarttv.screen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

import kodi.tv.iptv.m3u.smarttv.botom.BottomBar
import kodi.tv.iptv.m3u.smarttv.botom.MyBottomNavBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = { MyBottomNavBar() { selectedItem = it } }
    ) { int ->
        when (selectedItem) {
            0 -> HomeScreen(navController)
            1 -> M3uScreen(navController)
            2 -> ChannelScreen(navController)
        }

    }
}

