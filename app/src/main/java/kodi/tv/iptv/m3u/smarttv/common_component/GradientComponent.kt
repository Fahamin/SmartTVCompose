package com.hulu.peacoke.peacoketv.ui.common_component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import kodi.tv.iptv.m3u.smarttv.ui.theme.gradientColor1
import kodi.tv.iptv.m3u.smarttv.ui.theme.gradientColor2

@Composable
fun gradientColor(): Brush {
    return Brush.verticalGradient(
        colors = listOf(gradientColor1, gradientColor2)
    )
}