package com.sungbin.kakaoemoticonparser.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.ui.composable.RotateIcon
import com.sungbin.kakaoemoticonparser.ui.home.HomeContent
import com.sungbin.kakaoemoticonparser.ui.test.TestContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { MainContent() }
    }

    @Preview
    @Composable
    private fun MainContent() {
        val navigationState = rememberSaveable { mutableStateOf(NavigationType.SEARCH) }

        Column {
            NavigationFragmentContent(
                contentType = navigationState.value,
                modifier = Modifier.weight(1f)
            )
            NavigationBarContent(contentType = navigationState)
        }
    }

    @Composable
    private fun NavigationFragmentContent(
        modifier: Modifier = Modifier,
        contentType: NavigationType
    ) {
        Column(modifier = modifier) {
            Crossfade(contentType) { type ->
                when (type) {
                    NavigationType.SEARCH -> HomeContent()
                    else -> TestContent()
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
                        imageVector = Icons.Outlined.Favorite,
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
