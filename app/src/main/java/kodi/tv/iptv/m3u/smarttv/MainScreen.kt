package kodi.tv.iptv.m3u.smarttv

import android.annotation.SuppressLint
import android.net.VpnService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kodi.tv.iptv.m3u.smarttv.botom.BottomBar
import kodi.tv.iptv.m3u.smarttv.botom.BottomBarNavigation


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        /*topBar = {
            *//*  title = { Text(text = stringResource(id = titleRes)) },
              actions = {*//*
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Centered TopAppBar",
                        maxLines = 1,
                        color = Color.Blue,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        },*/
        bottomBar = {
            BottomBar(
                navController = navController
            )
        }
    ) {
        BottomNavigateScreen(
            context = context,
            navController
            = navController
        )
    }
}
