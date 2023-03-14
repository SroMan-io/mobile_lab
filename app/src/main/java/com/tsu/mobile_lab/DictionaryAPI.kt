package com.tsu.mobile_lab

import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {
    @GET("{word}")
    suspend fun getWordInfo(@Path("word") word: String): ArrayList<Word>
}