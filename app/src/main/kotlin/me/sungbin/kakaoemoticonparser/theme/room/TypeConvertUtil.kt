package me.sungbin.kakaoemoticonparser.theme.room

import me.sungbin.kakaoemoticonparser.theme.ColorPallet

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
}
