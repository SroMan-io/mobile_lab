package com.tsu.mobile_lab

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MeaningEntityDB(val Word: String, val Meaning: String, val Example: String,
                           @PrimaryKey(autoGenerate = true) val Id: Int = 0)
