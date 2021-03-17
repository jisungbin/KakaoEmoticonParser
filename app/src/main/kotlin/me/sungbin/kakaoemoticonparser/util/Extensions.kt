package me.sungbin.kakaoemoticonparser.util

import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.theme.ColorPallet
import me.sungbin.kakaoemoticonparser.theme.blue700
import me.sungbin.kakaoemoticonparser.theme.green700
import me.sungbin.kakaoemoticonparser.theme.orange700
import me.sungbin.kakaoemoticonparser.theme.purple700

fun AppThemeState.parseColor() = when (pallet) {
    ColorPallet.GREEN -> green700
    ColorPallet.BLUE -> blue700
    ColorPallet.ORANGE -> orange700
    ColorPallet.PURPLE -> purple700
}
