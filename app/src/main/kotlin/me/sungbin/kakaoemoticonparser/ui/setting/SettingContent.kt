package me.sungbin.kakaoemoticonparser.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.theme.AppThemeState

@Composable
fun SettingContent(appThemeState: MutableState<AppThemeState>) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.margin_default))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.margin_half))
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.app_name)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingContent() {
    SettingContent(mutableStateOf(AppThemeState()))
}
