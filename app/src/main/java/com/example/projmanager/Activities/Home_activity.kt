package com.example.projmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projmanager.Activities.login
import com.example.projmanager.R
import com.google.firebase.auth.FirebaseAuth

class home_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, login::class.java))
        finish()
    }
}