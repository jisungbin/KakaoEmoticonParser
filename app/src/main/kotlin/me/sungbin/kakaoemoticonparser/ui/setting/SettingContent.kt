package me.sungbin.kakaoemoticonparser.ui.setting

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.sungbin.androidutils.util.DialogUtil
import me.sungbin.androidutils.util.Logger
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.theme.AppMaterialTheme
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.theme.room.ThemeDatabase
import me.sungbin.kakaoemoticonparser.theme.room.ThemeEntity
import me.sungbin.kakaoemoticonparser.theme.room.TypeConvertUtil
import me.sungbin.kakaoemoticonparser.theme.typography

@Composable
fun SettingContent(appThemeState: MutableState<AppThemeState>) {
    val context = LocalContext.current
    val showMenu = remember { mutableStateOf(false) }
    BindThemeSettingMenu(showMenu, appThemeState)
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.margin_default))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = null,
                modifier = Modifier.size(125.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.margin_half))
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = typography.body1,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.margin_default))
                )
                Text(
                    text = stringResource(R.string.copyright),
                    style = typography.caption,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_default))
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.margin_twice)),
                onClick = {
                    DialogUtil.showOnce(
                        context as Activity,
                        context.getString(R.string.experiment_function_label),
                        context.getString(R.string.experiment_function_description),
                        "color-theme-change"
                    )
                    showMenu.value = !showMenu.value
                }
            ) {
                Text(
                    text = stringResource(R.string.setting_change_theme),
                    style = typography.button
                )
            }
            Button(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.margin_twice)),
                onClick = {
                    DialogUtil.showLicense(
                        context as Activity,
                        "opensource-license",
                        listOf(

                        )
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.setting_opensource_license),
                    style = typography.button
                )
            }
        }
    }
}

@Composable
private fun BindThemeSettingMenu(
    showMenu: MutableState<Boolean>,
    appThemeState: MutableState<AppThemeState>
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val themeDatabase = remember { ThemeDatabase.instance(context).dao() }
    val items = listOf("보라색", "초록색", "주황색", "파란색")
    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { showMenu.value = false },
        modifier = Modifier.animateContentSize()
    ) {
        items.forEachIndexed { index, title ->
            DropdownMenuItem(
                onClick = {
                    val newAppThemeState =
                        appThemeState.value.copy(pallet = TypeConvertUtil.intToPallet(index))
                    Logger.w(TypeConvertUtil.intToPallet(index))
                    appThemeState.value = newAppThemeState
                    showMenu.value = false
                    coroutineScope.launch {
                        themeDatabase.insert(
                            ThemeEntity(
                                isDarkTheme = newAppThemeState.isDarkMode,
                                colorPallet = index
                            )
                        )
                    }
                }
            ) {
                Text(
                    text = title,
                    color = TypeConvertUtil.intToColor(index)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingContent() {
    val appThemeState = mutableStateOf(AppThemeState())
    AppMaterialTheme(appThemeState.value) {
        SettingContent(appThemeState)
    }
}
