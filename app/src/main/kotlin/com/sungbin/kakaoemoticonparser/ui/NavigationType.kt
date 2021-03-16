package com.sungbin.kakaoemoticonparser.ui

sealed class NavigationType {
    object SEARCH : NavigationType()
    object FAVORITE : NavigationType()
    object SETTING : NavigationType()
}
