package me.sungbin.kakaoemoticonparser.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.sungbin.kakaoemoticonparser.R

private val defaultFontFamily = FontFamily(Font(R.font.nanumgothic))

val typography = Typography(
    defaultFontFamily = defaultFontFamily,
    body1 = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )
)
