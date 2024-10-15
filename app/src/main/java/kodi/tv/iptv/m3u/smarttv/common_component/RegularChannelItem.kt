package kodi.tv.iptv.m3u.smarttv.common_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import kodi.tv.iptv.m3u.smarttv.R
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.ui.theme.borderColor
import kodi.tv.iptv.m3u.smarttv.ui.theme.dimens

@Composable
fun RegularChannelItem(
    item: ChannelModel? = null,
    isFavoriteItem: Boolean = false,
    modifier: Modifier = Modifier.size(MaterialTheme.dimens.channelSmall),
    borderC: Color = borderColor,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    onItemClick: (ChannelModel) -> Unit = { },
    onFavClick: (Int) -> Unit = {}
) {
    val showShimmer = remember { mutableStateOf(true) }
    val context = LocalContext.current
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.clickable { onItemClick(item!!) },
        border = BorderStroke(width = 1.dp, color = borderC)
    ) {

        Box(
            modifier = Modifier.background(
                shimmerEffect(
                    targetValue = 1300f,
                    showShimmer = showShimmer.value
                )
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item?.logoUrl).diskCachePolicy(CachePolicy.ENABLED)
                        .setHeader("User-Agent", "Mozilla/5.0")
                        .build(),
                    contentScale = ContentScale.Fit,
                    contentDescription = context.getString(R.string.load_network_image),
                    onSuccess = {
                        showShimmer.value = false
                    }, onError = {
                        showShimmer.value = false
                    },
                    error = painterResource(R.drawable.baseline_live_tv_24)

                )
            }
            if (isFavoriteItem) {
                Box(
                    modifier = Modifier
                        .clickable {
                            onFavClick(item?.id!!)
                        }
                        .align(Alignment.BottomEnd)
                        .padding(end = 8.dp, bottom = 8.dp)
                ) {
                    GradientFavIcon()
                }
            }
        }

    }

}