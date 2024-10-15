package com.hulu.peacoke.peacoketv.ui.common_component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kodi.tv.iptv.m3u.smarttv.ui.theme.gradientColor1


@Composable
fun Loader(){
    CircularProgressIndicator(
        color = gradientColor1,
        strokeWidth = 2.dp)
}