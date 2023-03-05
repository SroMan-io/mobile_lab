package com.tsu.mobile_lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class IntroFirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_first)

        supportActionBar?.hide()
    }
}