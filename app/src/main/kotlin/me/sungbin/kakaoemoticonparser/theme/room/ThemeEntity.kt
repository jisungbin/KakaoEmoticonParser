package me.sungbin.kakaoemoticonparser.theme.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ThemeEntity(
    @PrimaryKey
    var key: Int = 999,
    var isDarkTheme: Boolean? = null,
    var colorPallet: Int? = null
)
