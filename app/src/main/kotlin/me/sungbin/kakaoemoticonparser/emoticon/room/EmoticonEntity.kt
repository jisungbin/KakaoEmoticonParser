package me.sungbin.kakaoemoticonparser.emoticon.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class EmoticonEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val title: String = "",
    val titleUrl: String = "",
    val imageUrl: String = "",
    val isBigEmo: Boolean = false,
    val isSound: Boolean = false
)
