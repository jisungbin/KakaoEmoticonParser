package com.sungbin.kakaoemoticonparser.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.theme.typography

@Composable
fun HomeContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column() {
                        Text(text = stringResource(R.string.app_name))
                        Text(
                            text = stringResource(R.string.copyright),
                            style = typography.subtitle2
                        )
                    }
                },
                elevation = dimensionResource(R.dimen.margin_half)
            )
        },
        content = {
            BindHomeContent()
        }
    )
}

@Composable
private fun BindHomeContent() {
    val searchText = remember { mutableStateOf(TextFieldValue()) }
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.search) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.margin_default))
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White,
                    RoundedCornerShape(dimensionResource(R.dimen.margin_default))
                ),
            value = searchText.value,
            onValueChange = { searchText.value = it },
            placeholder = { Text(stringResource(R.string.main_search_emoticon)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        )
        LottieAnimation(
            spec = animationSpec,
            animationState = animationState,
            modifier = Modifier
                .height(50.dp)
                .width(250.dp)
                .padding(top = dimensionResource(R.dimen.margin_default))
        )
    }
}
