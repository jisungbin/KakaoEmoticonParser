package com.sungbin.kakaoemoticonparser.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.sungbin.kakaoemoticonparser.R

@Composable
fun HomeContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(text = stringResource(id = R.string.app_name))
                        Text(
                            text = stringResource(id = R.string.copyright),
                            style = MaterialTheme.typography.body2
                        )
                    }
                },
                elevation = dimensionResource(id = R.dimen.margin_half)
            )
        },
        content = {
            BindHomeContent()
        }
    )
}

@Composable
private fun BindHomeContent() {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.search) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
    val lottieView = LottieAnimation(
        composition = animationSpec,
        state = animationState,
        modifier = Modifier.height(50.dp).width(250.dp)
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.margin_default))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.margin_half)),
            elevation = dimensionResource(id = R.dimen.margin_default)
        ) {

        }
    }
}
