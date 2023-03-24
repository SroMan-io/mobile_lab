package com.tsu.mobile_lab

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntityDB::class], version = 1)
abstract class WordDB: RoomDatabase() {
    abstract fun wordDao(): WordDao
}