package kodi.tv.iptv.m3u.smarttv.common_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kodi.tv.iptv.m3u.smarttv.ui.theme.gradientColor1
import kotlinx.coroutines.delay

@Composable
fun BasicTextField(
    isKeyboardShown: Boolean = false,
    inputValue: MutableState<String>,
    maxLine: Int = 1,
    placeholder: String = "",
    color: Color = MaterialTheme.colorScheme.tertiary,
    isShowIcon: Boolean = false,
    onIconClick:()->Unit= {  }
) {
    val showKeyboard = remember { mutableStateOf(isKeyboardShown) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    val density: Float = context.resources.displayMetrics.density

    androidx.compose.foundation.text.BasicTextField(

        value = inputValue.value,
        onValueChange = { newText ->
            inputValue.value = newText
        },

        textStyle = if (density <= 1.5){ MaterialTheme.typography.bodySmall} else MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        decorationBox = { innerTextField ->
            if (inputValue.value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = color,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (isShowIcon){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Box(modifier = Modifier.clickable { onIconClick() }
                        .clip(shape = CircleShape)
                        .background(color = gradientColor1)
                        .size(20.dp)) {
                        Icon(
                            modifier= Modifier.padding(4.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = gradientColor1
                            // modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            innerTextField()
        },
        singleLine = false,
        maxLines = maxLine,
        modifier = Modifier.focusRequester(focusRequester),
    )

    LaunchedEffect(focusRequester) {
        if (showKeyboard.value) {
            focusRequester.requestFocus()
            delay(100) // Make sure you have delay here
            keyboard?.show()
        }
    }

}