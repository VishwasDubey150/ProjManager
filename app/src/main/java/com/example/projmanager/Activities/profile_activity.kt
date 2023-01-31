package com.example.projmanager.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.projmanager.Firebase.FirestoreClass
import com.example.projmanager.R
import com.example.projmanager.home_activity
import com.example.projmanager.models.User

class profile_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        FirestoreClass().LoadUserData(this)
    }

    fun back(view: View) {
        val intent= Intent(this,home_activity::class.java)
        startActivity(intent)
        finish()
    }

    fun setUSerDataInUI(user: User)
    {
        val iv_user_image=findViewById<ImageView>(R.id.iv_user_image)
        val iv_username=findViewById<EditText>(R.id.iv_username)
        val iv_email=findViewById<EditText>(R.id.iv_email)
        val iv_moblie=findViewById<EditText>(R.id.iv_mobile)
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(iv_user_image)

        iv_username.setText(user.name)
        iv_email.setText(user.email)
        if(user.mobile != 0L)
        {
            iv_moblie.setText(user.mobile.toString())
        }
    }

}