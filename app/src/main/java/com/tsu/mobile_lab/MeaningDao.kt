package com.tsu.mobile_lab

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MeaningDao {

    @Insert
    fun insert(meaning: MeaningEntityDB)

    @Query("SELECT * FROM meaningentitydb WHERE word LIKE :userword")
    fun getWordInfo(userword: String): List<MeaningEntityDB>

}