package kodi.tv.iptv.m3u.smarttv.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.model.PlayListModel
import kodi.tv.iptv.m3u.smarttv.utils.M3UParserurl
import kodi.tv.iptv.m3u.smarttv.viewModel.DbViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var dbViewModel: DbViewModel? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var outletViewModel: OutletViewModel = hiltViewModel()
    dbViewModel = hiltViewModel<DbViewModel>()

    val outletResult by outletViewModel.outletResult.collectAsState()

    // Fetch outlets when the screen is loaded
    LaunchedEffect(Unit) {
        outletViewModel.fetchOutlets()
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

    Box(modifier = Modifier.fillMaxSize()){
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        for (i in outlets.indices) {
            parsing(outlets[i].link,outlets[i].name!!)
        }
        LazyColumn {
            items(outlets) { outlet ->
                Text(text = "Name: ${outlet.name}", color= MaterialTheme.colorScheme.tertiary)
            }
        }
    }


}

fun parsing(link: String?, playListName: String) {
    // Start a coroutine
    CoroutineScope(Dispatchers.IO).launch {
        // preExecute equivalent
        // showProgress();

        val parse: List<ChannelModel>? = M3UParserurl().parse(link)
        Log.e("fahamin", parse.toString())
        Log.e("fahamin", link.toString())

        if (parse != null) {
            val isAdd = dbViewModel!!.checkPlayList(link!!)
            if (!isAdd) {
                val model = PlayListModel().apply {
                    idPlayList = link.toString()
                    namePlayList = playListName
                    totalChannel = parse.size
                }

                dbViewModel!!.insertPlaylist(model)
                dbViewModel!!.insertChannel(parse)
            }

            // postExecute equivalent - run on the main thread
            withContext(Dispatchers.Main) {
                // Update UI if needed here, e.g. hideProgress();
            }
        }
    }
}

