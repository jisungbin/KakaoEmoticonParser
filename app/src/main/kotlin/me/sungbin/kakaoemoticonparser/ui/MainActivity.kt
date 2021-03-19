package me.sungbin.kakaoemoticonparser.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mahfa.dnswitch.DayNightSwitch
import me.sungbin.androidutils.util.DialogUtil
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.theme.AppMaterialTheme
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.theme.SystemUiController
import me.sungbin.kakaoemoticonparser.theme.typography
import me.sungbin.kakaoemoticonparser.ui.favorite.FavoriteContent
import me.sungbin.kakaoemoticonparser.ui.search.SearchContent
import me.sungbin.kakaoemoticonparser.ui.search.SearchContentState
import me.sungbin.kakaoemoticonparser.ui.setting.SettingContent
import me.sungbin.kakaoemoticonparser.ui.widget.RotateIcon
import me.sungbin.kakaoemoticonparser.util.parseColor

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val systemUiController = remember { SystemUiController(window) }
            val appThemeState = remember { mutableStateOf(AppThemeState().init(context)) }
            val errorMessage = remember { mutableStateOf("") }
            val searchState = rememberSaveable { mutableStateOf(SearchContentState.HOME) }

            BindView(appThemeState.value, systemUiController) {
                MainContent(appThemeState, errorMessage, searchState)
            }
        }
    }

    @Composable
    private fun BindView(
        appThemeState: AppThemeState,
        systemUiController: SystemUiController?,
        content: @Composable () -> Unit
    ) {
        systemUiController?.setStatusBarColor(appThemeState.parseColor(), appThemeState.isDarkMode)
        AppMaterialTheme(appThemeState) {
            content()
        }
    }

    @Composable
    private fun MainContent(
        appThemeState: MutableState<AppThemeState>,
        errorMessage: MutableState<String>,
        searchState: MutableState<SearchContentState>
    ) {
        val context = LocalContext.current
        val navigationState = rememberSaveable { mutableStateOf(NavigationType.SEARCH) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = typography.body1
                            )
                            Text(
                                text = stringResource(R.string.copyright),
                                style = typography.caption
                            )
                        }
                    },
                    elevation = dimensionResource(R.dimen.margin_half),
                    actions = {
                        val dayNightSwitch = remember {
                            DayNightSwitch(context).apply {
                                setIsNight(appThemeState.value.isDarkMode, true)
                                setListener { isNight ->
                                    DialogUtil.showOnce(
                                        context as Activity,
                                        context.getString(R.string.experiment_function_label),
                                        context.getString(R.string.experiment_function_description),
                                        "dark-theme-change"
                                    )
                                    appThemeState.value =
                                        appThemeState.value.copy(isDarkMode = isNight)
                                }
                            }
                        }
                        AndroidView(
                            factory = { dayNightSwitch },
                            modifier = Modifier
                                .size(70.dp, 30.dp)
                                .padding(end = dimensionResource(R.dimen.margin_default))
                        )
                    }
                )
            },
            content = {
                Column {
                    NavigationFragmentContent(
                        appThemeState = appThemeState,
                        contentType = navigationState.value,
                        modifier = Modifier.weight(1f),
                        errorMessage = errorMessage,
                        searchState = searchState,
                    )
                    NavigationBarContent(contentType = navigationState)
                }
            }
        )
    }

    @Composable
    private fun NavigationFragmentContent(
        appThemeState: MutableState<AppThemeState>,
        modifier: Modifier = Modifier,
        contentType: NavigationType,
        errorMessage: MutableState<String>,
        searchState: MutableState<SearchContentState>
    ) {
        Column(modifier = modifier) {
            Crossfade(contentType) { type ->
                Surface(color = MaterialTheme.colors.background) {
                    when (type) {
                        NavigationType.SEARCH -> SearchContent().Bind(
                            appThemeState.value,
                            errorMessage,
                            searchState
                        )
                        NavigationType.FAVORITE -> FavoriteContent(
                            appThemeState = appThemeState.value,
                            searchState = searchState,
                            errorMessage = errorMessage
                        )
                        NavigationType.SETTING -> SettingContent(appThemeState)
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
