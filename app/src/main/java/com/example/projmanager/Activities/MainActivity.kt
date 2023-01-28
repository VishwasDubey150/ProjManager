package com.example.projmanager.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projmanager.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    fun signup_page(view: View) {
        val intent=Intent(this, Signup::class.java)
        startActivity(intent)
    }

    fun login(view: View) {
        val intent=Intent(this, login::class.java)
        startActivity(intent)
        finish()
    }
}