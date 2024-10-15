package com.hulu.peacoke.peacoketv.ui.common_component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import kodi.tv.iptv.m3u.smarttv.ui.theme.fonts_ballo_da

@Composable
fun TextView25W800(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start

) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = fonts_ballo_da,
            fontWeight = FontWeight.W800
        ),
        fontSize = 30.sp,
        textAlign = textAlign,
    )
}

@Composable
fun TextView18W500(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start

) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = fonts_ballo_da,
            fontWeight = FontWeight.W500
        ),
        textAlign = textAlign,
    )
}

@Composable
fun TextView16W500(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        textAlign = textAlign,
    )
}

@Composable
fun TextView14W400(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLine: Int = 1
) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLine
    )
}

@Composable
fun TextView14W400Gradient(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLine: Int = 1
) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500, brush = gradientColor()
        ),
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLine
    )
}


@Composable
fun TextView14W500(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLine: Int = 1
) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLine
    )
}

@Composable
fun TextView12W400(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Visible,
) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.bodySmall,
        textAlign = textAlign,
        overflow = overflow,

    )
}

@Composable
fun TextView10W400(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = value,
        color = color,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
        textAlign = textAlign,
    )
}
