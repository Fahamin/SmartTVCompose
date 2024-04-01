package kodi.tv.iptv.m3u.smarttv.screen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import kodi.tv.iptv.m3u.smarttv.BottomNavigateScreen
import kodi.tv.iptv.m3u.smarttv.botom.BottomBar


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
