package me.sungbin.kakaoemoticonparser.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.theme.AppTheme
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.theme.SystemUiController
import me.sungbin.kakaoemoticonparser.ui.search.SearchContent
import me.sungbin.kakaoemoticonparser.ui.setting.SettingContent
import me.sungbin.kakaoemoticonparser.ui.test.TestContent
import me.sungbin.kakaoemoticonparser.ui.widget.RotateIcon
import me.sungbin.kakaoemoticonparser.util.parseColor

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val systemUiController = remember { SystemUiController(window) }
            val appThemeState = remember { mutableStateOf(AppThemeState().init(context)) }
            BindView(appThemeState.value, systemUiController) {
                MainContent(appThemeState)
            }
        }
    }

    @Composable
    private fun BindView(
        appThemeState: AppThemeState,
        systemUiController: SystemUiController?,
        content: @Composable () -> Unit
    ) {
        systemUiController?.setStatusBarColor(appThemeState.parseColor(), appThemeState.darkTheme)
        AppTheme(appThemeState) {
            content()
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun MainContent(appThemeState: MutableState<AppThemeState>) {
        val navigationState = rememberSaveable { mutableStateOf(NavigationType.SEARCH) }

        Column {
            NavigationFragmentContent(
                appThemeState = appThemeState,
                contentType = navigationState.value,
                modifier = Modifier.weight(1f)
            )
            NavigationBarContent(contentType = navigationState)
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun NavigationFragmentContent(
        appThemeState: MutableState<AppThemeState>,
        modifier: Modifier = Modifier,
        contentType: NavigationType
    ) {
        Column(modifier = modifier) {
            Crossfade(contentType) { type ->
                Surface(color = MaterialTheme.colors.background) {
                    when (type) {
                        NavigationType.SEARCH -> SearchContent().Bind(appThemeState.value)
                        NavigationType.SETTING -> SettingContent(appThemeState)
                        else -> TestContent()
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationBarContent(
        modifier: Modifier = Modifier,
        contentType: MutableState<NavigationType>
    ) {
        var animate by remember { mutableStateOf(false) }
        BottomNavigation(modifier = modifier) {
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
                selected = contentType.value == NavigationType.SEARCH,
                onClick = {
                    contentType.value = NavigationType.SEARCH
                    animate = false
                },
                label = { Text(text = stringResource(id = R.string.navigation_search)) }
            )
            BottomNavigationItem(
                icon = {
                    RotateIcon(
                        state = animate,
                        imageVector = Icons.Outlined.FavoriteBorder,
                        angle = 720f,
                        duration = 2000
                    )
                },
                selected = contentType.value == NavigationType.FAVORITE,
                onClick = {
                    contentType.value = NavigationType.FAVORITE
                    animate = true
                },
                label = { Text(text = stringResource(id = R.string.navigation_favorite)) }
            )
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
                selected = contentType.value == NavigationType.SETTING,
                onClick = {
                    contentType.value = NavigationType.SETTING
                    animate = false
                },
                label = { Text(text = stringResource(id = R.string.navigation_setting)) }
            )
        }
    }
}
