package me.sungbin.kakaoemoticonparser.emoticon.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmoticonDao {

    @Query("SELECT * FROM EmoticonEntity WHERE `title` = :title")
    suspend fun getFavoriteEmoticon(title: String): EmoticonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(title: EmoticonEntity)

    @Delete
    suspend fun delete(title: EmoticonEntity)
}
