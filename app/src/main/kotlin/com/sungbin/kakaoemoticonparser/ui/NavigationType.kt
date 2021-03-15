package com.sungbin.kakaoemoticonparser.ui

sealed class NavigationType {
    object HOME : NavigationType()
    object FAVORITE : NavigationType()
    object SETTING : NavigationType()
}
