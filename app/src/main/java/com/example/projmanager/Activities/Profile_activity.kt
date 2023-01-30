package com.example.projmanager.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projmanager.R

class profile_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
    }
}