package kodi.tv.iptv.m3u.smarttv.common_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kodi.tv.iptv.m3u.smarttv.ui.theme.gradientColor1
import kodi.tv.iptv.m3u.smarttv.ui.theme.gradientColor2

@Composable
fun GradientFavIcon(
    size: Dp = 16.dp,
    isFavorite: Boolean = true,
    onFavClick: (Boolean) -> Unit = {}
) {
    val gradient = Brush.linearGradient(
        colors = listOf(
            gradientColor1,
            gradientColor2,
        ),
        start = Offset(12f, 12f),
        end = Offset(12f, 52f),
    )
    Icon(
        modifier = Modifier
            .clickable { onFavClick(isFavorite) }
            .size(size)
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.SrcAtop)
                }
            },
        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = null,
    )
}