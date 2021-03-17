package com.sungbin.kakaoemoticonparser.util

import com.sungbin.kakaoemoticonparser.theme.AppThemeState
import com.sungbin.kakaoemoticonparser.theme.ColorPallet
import com.sungbin.kakaoemoticonparser.theme.blue700
import com.sungbin.kakaoemoticonparser.theme.green700
import com.sungbin.kakaoemoticonparser.theme.orange700
import com.sungbin.kakaoemoticonparser.theme.purple700

fun AppThemeState.parseColor() = when (pallet) {
    ColorPallet.GREEN -> green700
    ColorPallet.BLUE -> blue700
    ColorPallet.ORANGE -> orange700
    ColorPallet.PURPLE -> purple700
}
