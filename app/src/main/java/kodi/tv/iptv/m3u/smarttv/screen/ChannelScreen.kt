package kodi.tv.iptv.m3u.smarttv.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kodi.tv.iptv.m3u.smarttv.MainActivity
import kodi.tv.iptv.m3u.smarttv.common_component.RegularChannelItem
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.ui.theme.dimens
import kodi.tv.iptv.m3u.smarttv.utils.Fun.Companion.nc
import kodi.tv.iptv.m3u.smarttv.utils.admob.LoadNativeAd
import kodi.tv.iptv.m3u.smarttv.utils.admob.NativeAdViewSmall
import kodi.tv.iptv.m3u.smarttv.viewModel.DbViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.PlayerViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.SearchChannelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelScreen(
    viewModel: DbViewModel,
    channelViewModel: PlayerViewModel,
    searchChannelViewModel: SearchChannelViewModel,
    activity: Activity = LocalContext.current as MainActivity,
    navController: NavController
) {
    val selectedIndex = remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        channelViewModel.callChannelDataByCatId()
    })

    Column {


        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            channelViewModel.channelData.observeAsState().value?.let {
                //  HeaderText(viewModel.channelCategoryName.observeAsState().value)

                LazyVerticalGrid(
                    modifier = Modifier.height(((MaterialTheme.dimens.gridItemHeight * it.size) / 2).dp),
                    columns = GridCells.Fixed(MaterialTheme.dimens.gridCellsChannel),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.stdDimen12),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.stdDimen12),
                    userScrollEnabled = false,
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        top = 10.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    )
                ) {
                    items(it.size + it.size / nc) { index ->
                        if (index % (nc + 1) == nc) {
                            // Place an ad view
                            var nativAd = LoadNativeAd(context = context)
                            NativeAdViewSmall(nativAd)
                        } else {
                            // Calculate the actual item index considering the ads
                            val actualItemIndex = index - index / (nc + 1)

                            RegularChannelItem(
                                item = it.get(actualItemIndex),
                                modifier = Modifier.height(MaterialTheme.dimens.channelMedium),
                                onItemClick = { clickedItem ->
                                    //    loadInterstitialAdd(context)
                                    // channelViewModel.addTOFrequentChannel(clickedItem.id!!)
                                    channelViewModel.setSelectedChannel(clickedItem)
                                    navController.navigate(Routes.DetailScreen)
                                },
                            )
                        }
                    }
                }
            }

        }
    }

}
