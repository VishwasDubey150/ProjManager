package com.example.projmanager.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projmanager.R
import com.example.projmanager.home_activity

class create_board : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)
        supportActionBar?.hide()

    }

    fun backC(view: View) {
        startActivity(Intent(this,home_activity::class.java))
    }
}