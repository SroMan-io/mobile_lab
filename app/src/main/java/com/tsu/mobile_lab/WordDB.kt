package com.tsu.mobile_lab

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntityDB::class, MeaningEntityDB::class], version = 4)
abstract class WordDB: RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun meaningDao(): MeaningDao
}