package com.example.projmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.MenuItem
import android.view.View
import com.example.projmanager.Activities.login
import com.example.projmanager.Activities.profile_activity
import com.example.projmanager.R
import com.google.firebase.auth.FirebaseAuth

class home_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
    }
    fun logout(item: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, login::class.java))
        finish()
    }

    fun profile(item: MenuItem) {
        val intent=Intent(this, profile_activity::class.java)
        startActivity(intent)
    }

    private fun setupActionBar()
    {

    }
}