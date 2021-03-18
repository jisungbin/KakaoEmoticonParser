package me.sungbin.kakaoemoticonparser.emoticon.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmoticonEntity::class], version = 1)
abstract class EmoticonDatabase : RoomDatabase() {
    abstract fun dao(): EmoticonDao

    companion object {
        private lateinit var emoticonDatabase: EmoticonDatabase

        fun instance(context: Context): EmoticonDatabase {
            if (!::emoticonDatabase.isInitialized) {
                synchronized(EmoticonDatabase::class) {
                    emoticonDatabase = Room.databaseBuilder(
                        context,
                        EmoticonDatabase::class.java,
                        "emoticon.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return emoticonDatabase
        }
    }
}
