package com.example.projmanager.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.projmanager.Activities.MainActivity
import com.example.projmanager.Firebase.FirestoreClass
import com.example.projmanager.R
import com.example.projmanager.home_activity
import com.google.firebase.ktx.Firebase

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler().postDelayed({
            var currentUserID=FirestoreClass().getCurrnetUserId()

            if (currentUserID.isNotEmpty())
            {
                startActivity(Intent(this,home_activity::class.java))
            }
            else
            {
                startActivity(Intent(this, MainActivity::class.java))

            }
            finish()
        },500)
    }
}