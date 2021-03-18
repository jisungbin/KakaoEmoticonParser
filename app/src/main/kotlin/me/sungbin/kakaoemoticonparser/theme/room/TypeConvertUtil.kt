package me.sungbin.kakaoemoticonparser.theme.room

import me.sungbin.kakaoemoticonparser.theme.ColorPallet
import me.sungbin.kakaoemoticonparser.theme.blue700
import me.sungbin.kakaoemoticonparser.theme.green700
import me.sungbin.kakaoemoticonparser.theme.orange700
import me.sungbin.kakaoemoticonparser.theme.purple700

object TypeConvertUtil {
    fun palletToInt(pallet: ColorPallet) = when (pallet) {
        ColorPallet.PURPLE -> 0
        ColorPallet.GREEN -> 1
        ColorPallet.ORANGE -> 2
        ColorPallet.BLUE -> 3
    }

    fun intToPallet(number: Int) = when (number) {
        0 -> ColorPallet.PURPLE
        1 -> ColorPallet.GREEN
        2 -> ColorPallet.ORANGE
        else -> ColorPallet.BLUE // 3
    }

    fun intToColor(number: Int) = when (number) {
        0 -> purple700
        1 -> green700
        2 -> orange700
        else -> blue700 // 3
    }
}
