package me.sungbin.kakaoemoticonparser.theme.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ThemeEntity::class], version = 3)
abstract class ThemeDatabase : RoomDatabase() {
    abstract fun dao(): ThemeDao

    companion object {
        private lateinit var themeDatabase: ThemeDatabase

        fun instance(context: Context): ThemeDatabase {
            if (!::themeDatabase.isInitialized) {
                synchronized(ThemeDatabase::class) {
                    themeDatabase = Room.databaseBuilder(
                        context,
                        ThemeDatabase::class.java,
                        "theme.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return themeDatabase
        }
    }
}
