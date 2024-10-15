package kodi.tv.iptv.m3u.smarttv.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kodi.tv.iptv.m3u.smarttv.model.M3uModel
import kodi.tv.iptv.m3u.smarttv.utils.NetworkResult
import kodi.tv.iptv.m3u.smarttv.viewModel.OutletViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var viewModel: OutletViewModel = hiltViewModel()

    val outletResult by viewModel.outletResult.collectAsState()

    // Fetch outlets when the screen is loaded
    LaunchedEffect(Unit) {
        viewModel.fetchOutlets()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (outletResult) {
            is NetworkResult.Loading -> {
                // Show a loading spinner
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is NetworkResult.Success -> {
                val outlets = (outletResult as NetworkResult.Success<List<M3uModel>>).data
                if (outlets != null) {
                    OutletList(outlets)
                }
            }

            is NetworkResult.Error -> {
                val errorMessage = (outletResult as NetworkResult.Error).message
                Text(text = "Error: $errorMessage", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun OutletList(outlets: List<M3uModel>) {
    // Display list of outlets
    LazyColumn {
        items(outlets) { outlet ->
            Text(text = "Name: ${outlet.name}")
        }
    }
}

