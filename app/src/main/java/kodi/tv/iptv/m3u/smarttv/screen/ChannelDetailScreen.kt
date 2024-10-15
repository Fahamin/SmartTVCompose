package kodi.tv.iptv.m3u.smarttv.screen

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hulu.peacoke.peacoketv.ui.common_component.SpacerHeight
import com.hulu.peacoke.peacoketv.ui.common_component.SpacerWidth
import com.hulu.peacoke.peacoketv.ui.common_component.TextView12W400
import com.hulu.peacoke.peacoketv.ui.common_component.TextView14W500
import com.hulu.peacoke.peacoketv.ui.common_component.TextView18W500
import com.peacoke.peacoketv.utils.setLandscape
import com.peacoke.peacoketv.utils.setPortrait
import kodi.tv.iptv.m3u.smarttv.MainActivity
import kodi.tv.iptv.m3u.smarttv.R
import kodi.tv.iptv.m3u.smarttv.common_component.BasicTextField
import kodi.tv.iptv.m3u.smarttv.common_component.ChannelItem
import kodi.tv.iptv.m3u.smarttv.common_component.GradientFavIcon
import kodi.tv.iptv.m3u.smarttv.common_component.RegularChannelItem
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.model.M3uModel
import kodi.tv.iptv.m3u.smarttv.player.PlayerScreen
import kodi.tv.iptv.m3u.smarttv.repository.DbRepository
import kodi.tv.iptv.m3u.smarttv.ui.theme.ScreenOrientation
import kodi.tv.iptv.m3u.smarttv.ui.theme.darkBackground
import kodi.tv.iptv.m3u.smarttv.ui.theme.dimens
import kodi.tv.iptv.m3u.smarttv.ui.theme.lightBackground
import kodi.tv.iptv.m3u.smarttv.utils.Constants.PLAYER_CONTROLS_VISIBILITY
import kodi.tv.iptv.m3u.smarttv.utils.Constants.hideSystemUI
import kodi.tv.iptv.m3u.smarttv.utils.Fun
import kodi.tv.iptv.m3u.smarttv.utils.Fun.Companion.nc
import kodi.tv.iptv.m3u.smarttv.utils.admob.AdmobBanner
import kodi.tv.iptv.m3u.smarttv.utils.admob.LoadNativeAd
import kodi.tv.iptv.m3u.smarttv.utils.admob.NativeAdViewSmall
import kodi.tv.iptv.m3u.smarttv.viewModel.DbViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.PlayerViewModel
import kodi.tv.iptv.m3u.smarttv.viewModel.SearchChannelViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun ChannelDetailScreen(
    viewModel:  DbViewModel,
    channelViewModel: PlayerViewModel,
    searchChannelViewModel: SearchChannelViewModel,
    activity: Activity = LocalContext.current as MainActivity,
    navController: NavController
) {
    val context = LocalContext.current
    val value = remember { mutableStateOf("") }


    LaunchedEffect(key1 = value.value, block = {

        searchChannelViewModel.searchChannel(value.value)
    })
    var shouldShowControls by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = shouldShowControls) {
        Fun(context)

        if (shouldShowControls) {
            delay(PLAYER_CONTROLS_VISIBILITY)
            shouldShowControls = false
        }
    }
    /*LaunchedEffect(key1 = true, block = {
        channelViewModel.catId = channelViewModel.selectedChannel.value?.catId!!
        //channelViewModel.callChannelDataByCatId()
        channelViewModel.checkFavorite(channelViewModel.selectedChannel.value?.id!!)
        viewModel.getCategoryNameById(channelViewModel.catId)
    })*/

    if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)

            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            BasicTextField(
                                isKeyboardShown = false,
                                inputValue = value,

                                placeholder = context.getString(R.string.search_place_holder)
                            )
                        }
                    }
                }
            }
            /*MainTopBar(isBackEnable = true, navigateBack = {
                navController.popBackStack()
            }, onSearchIconClick = {navController.navigate(Routing.SearchScreen.routeName) })*/


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(MaterialTheme.dimens.mediumWeightTv, fill = true)
            ) {
                PlayerScreen(
                    videoUrl = channelViewModel.selectedChannel,
                    isFullScreen = false, navigateBack = {
                        navController.popBackStack()
                    }
                ) {
                    shouldShowControls = shouldShowControls.not()

                }
                this@Column.AnimatedVisibility(
                    modifier = Modifier.fillMaxSize(),
                    visible = shouldShowControls,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier.background(darkBackground.copy(alpha = 0.6f))
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 16.dp, bottom = 16.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .clickable { context.setLandscape() },
                                contentScale = ContentScale.Crop,
                                painter = painterResource(
                                    id = R.drawable.full_screen_entry
                                ),
                                contentDescription = ""
                            )
                        }
                    }
                }
                //http://ert-live-bcbs15228.siliconweb.com/media/ert_world/ert_worldmedium.m3u8
                //https://mediashohayprod-aase.streaming.media.azure.net/26a9dc05-ea5b-4f23-a3bb-cc48d96e605b/video-24-1687293003062-media-24.ism/manifest(format=m3u8-aapl)
            }

            SpacerHeight(MaterialTheme.dimens.stdDimen12)
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                   /* RegularChannelItem(
                        modifier = Modifier.size(MaterialTheme.dimens.channelExtraSmall),
                        item = ChannelDto(iconUrl = channelViewModel.selectedChannel.observeAsState().value?.iconUrl)
                    )*/
                    SpacerWidth(MaterialTheme.dimens.stdDimen10)

                    Column {
                        TextView14W500(
                            value = channelViewModel.selectedChannel.observeAsState().value?.title
                                ?: "N/A"
                        )

                    }
                }
                /*channelViewModel.isFavoriteChannel.observeAsState().value?.let {
                    GradientFavIcon(
                        size = 24.dp,
                        isFavorite = it
                    ) { isFav ->
                        if (isFav) {
                            channelViewModel.removeFavoriteChannel(channelViewModel.selectedChannel.value?.id!!)
                        } else {
                            channelViewModel.setFavoriteChannel(channelViewModel.selectedChannel.value!!)
                        }
                    }
                }*/
            }
            SpacerHeight(MaterialTheme.dimens.stdDimen12)

            AdmobBanner()
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(2f, fill = true)
            ) {
                searchChannelViewModel.channelData.observeAsState().value?.let {
                   // HeaderText(viewModel.channelCategoryName.observeAsState().value)

                    LazyVerticalGrid(
                        modifier = Modifier.height(((MaterialTheme.dimens.gridItemHeight * it.size) / 2).dp),
                        columns = GridCells.Fixed(MaterialTheme.dimens.gridCellsChannel),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
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
                                  //      loadInterstitialAdd(context)
                                        channelViewModel.setSelectedChannel(clickedItem)
                                     //   channelViewModel.checkFavorite(channelViewModel.selectedChannel.value?.id!!)
                                       // channelViewModel.addTOFrequentChannel(clickedItem.id!!)

                                    },
                                )
                            }
                        }
                    }
                }

            }


        }
    } else {
        hideSystemUI(activity)

        Box {
            PlayerScreen(
                videoUrl = channelViewModel.selectedChannel,
                isFullScreen = true,
            ) {
                shouldShowControls = shouldShowControls.not()
            }
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = shouldShowControls,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkBackground.copy(alpha = 0.6f))
                ) {
                    Box(
                        modifier = Modifier

                            .align(Alignment.TopStart)
                            .padding(start = 16.dp, top = 16.dp)
                    ) {
                        TextView18W500(
                            value = channelViewModel.selectedChannel.observeAsState().value?.title
                                ?: "N/A",
                            color = lightBackground
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 32.dp, bottom = 32.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .clickable { context.setPortrait() },
                            contentScale = ContentScale.Crop,
                            painter = painterResource(
                                id = R.drawable.full_screen_exit
                            ),
                            contentDescription = ""
                        )
                    }
                }

            }
        }
    }
}