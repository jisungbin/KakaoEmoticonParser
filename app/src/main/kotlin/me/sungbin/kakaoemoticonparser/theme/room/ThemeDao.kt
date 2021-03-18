package me.sungbin.kakaoemoticonparser.theme.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ThemeDao {

    @Query("SELECT * FROM ThemeEntity")
    suspend fun getTheme(): ThemeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(theme: ThemeEntity)
}
