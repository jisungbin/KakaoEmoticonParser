package com.sungbin.kakaoemoticonparser.util.extensions

/**
 * Created by SungBin on 2020-08-13.
 */

fun String.parse(
    startValue: String,
    endValue: String = "</$startValue>",
    index: Int,
    inputAuto: Boolean = true
) = this.split(
    if (inputAuto) {
        if (startValue.contains("<")) startValue else "<$startValue>"
    } else startValue
)[index].split(endValue)[0]
