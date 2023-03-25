package com.tsu.mobile_lab

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Insert
    fun insert (word: WordEntityDB)

    @Query("SELECT * FROM wordentitydb")
    fun getAllMeanings(): List<WordEntityDB>

    @Query("SELECT * FROM wordentitydb WHERE word LIKE :userword")
    fun findWord(userword: String): WordEntityDB

    @Delete
    fun delete(word: WordEntityDB)
}