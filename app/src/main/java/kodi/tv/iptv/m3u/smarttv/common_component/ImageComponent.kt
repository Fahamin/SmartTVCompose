package com.hulu.peacoke.peacoketv.ui.common_component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun ImageComponent(drawableId:Int,modifier: Modifier=Modifier,contentScale: ContentScale= ContentScale.Fit) {
    Image(
        painterResource(drawableId),
        contentDescription = null,
        modifier = modifier,
        contentScale =contentScale
    )
}