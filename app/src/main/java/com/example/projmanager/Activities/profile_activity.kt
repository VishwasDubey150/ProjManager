package com.example.projmanager.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projmanager.R
import com.example.projmanager.home_activity

class profile_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
    }

    fun back(view: View) {
        val intent= Intent(this,home_activity::class.java)
        startActivity(intent)
        finish()
    }
}