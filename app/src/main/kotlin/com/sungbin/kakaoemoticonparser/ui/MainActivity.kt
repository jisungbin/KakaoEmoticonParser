package com.sungbin.kakaoemoticonparser.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
        }
    }

    @Composable
    private fun MainContent() {
        val navigationState = rememberSaveable { mutableStateOf(NavigationType.HOME) }

        Column {
        }
    }

    @Composable
    private fun NavigationContent(contentType: NavigationType) {
        Column {
            Crossfade(contentType) { type ->
                when (type) {
                    NavigationType.HOME -> HomeScreen()
                }
            }
        }
    }


}
