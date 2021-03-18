package me.sungbin.kakaoemoticonparser.theme

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.kakaoemoticonparser.theme.room.ThemeDatabase
import me.sungbin.kakaoemoticonparser.theme.room.TypeConvertUtil

data class AppThemeState(
    var darkTheme: Boolean = false,
    var pallet: ColorPallet = ColorPallet.BLUE
) {
    fun init(context: Context): AppThemeState {
        val themeDatabase = ThemeDatabase.instance(context)
        CoroutineScope(Dispatchers.IO).launch {
            val themeEntity = themeDatabase.dao().getTheme()
            darkTheme = themeEntity.isDarkTheme
            pallet = TypeConvertUtil.intToPallet(themeEntity.colorPallet)
        }
        return this
    }
}
