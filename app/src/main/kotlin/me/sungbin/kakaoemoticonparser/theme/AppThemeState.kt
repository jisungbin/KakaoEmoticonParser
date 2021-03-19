package me.sungbin.kakaoemoticonparser.theme

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.kakaoemoticonparser.theme.room.ThemeDatabase
import me.sungbin.kakaoemoticonparser.theme.room.TypeConvertUtil

data class AppThemeState(
    var isDarkMode: Boolean = false,
    var pallet: ColorPallet = ColorPallet.BLUE
) {
    fun init(context: Context): AppThemeState {
        val themeDatabase = ThemeDatabase.instance(context).dao()
        CoroutineScope(Dispatchers.IO).launch {
            val themeEntity = themeDatabase.getTheme()
            isDarkMode = themeEntity?.isDarkTheme ?: false
            pallet = TypeConvertUtil.intToPallet(themeEntity?.colorPallet ?: 3) // BLUE
        }
        return this
    }
}
