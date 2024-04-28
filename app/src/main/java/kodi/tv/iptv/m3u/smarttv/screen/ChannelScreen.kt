package kodi.tv.iptv.m3u.smarttv.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kodi.tv.iptv.m3u.smarttv.R
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.viewModel.DbViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<DbViewModel>()

    val searchText by viewModel.searchText.collectAsState()
    val channelList by viewModel.persons.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    // var channelList by remember { mutableStateOf(emptyList<ChannelModel>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                var selectedIndex = 0
                //  Log.e("fahamin", itemList[0].name.toString())
                if (channelList!!.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn {
                        itemsIndexed(items = channelList!!) { index, item ->
                            ListChannel(
                                model = item, index, selectedIndex
                            ) { i ->
                                selectedIndex = i

                                navController.navigate("${Routes.player1}?name=${channelList[i].path}?cat=news")
                            }
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun ListChannel(
    model: ChannelModel, index: Int, selectedIndex: Int, onClick: (Int) -> Unit
) {

    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .fillMaxWidth()
        .clickable { onClick(index) }, shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            AsyncImage(
                modifier = Modifier
                    .size(width = 60.dp, height = 60.dp)
                    .align(Alignment.CenterHorizontally),
                model = model.logoUrl.toString(),
                placeholder = painterResource(id = R.drawable.baseline_live_tv_24),
                error = painterResource(id = R.drawable.baseline_live_tv_24),
                contentDescription = "The delasign logo",
            )
            androidx.compose.material.Text(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                text = model.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(5.dp))
        }


    }

}

