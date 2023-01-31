package com.example.projmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.projmanager.Activities.login
import com.example.projmanager.Activities.profile_activity
import com.example.projmanager.Firebase.FirestoreClass
import com.example.projmanager.models.User
import com.example.projmanager.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class home_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupActionBar()
        FirestoreClass().LoadUserData(this)
    }

    fun updateNavUserDetail(user:User)
    {
        val nav_user_image=findViewById<ImageView>(R.id.nav_user_image)
        val text_name=findViewById<TextView>(R.id.name_text)
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(nav_user_image)

        text_name.text=user.name
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
        var toolbar=findViewById<Toolbar>(R.id.toolbar_main_activity)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.hamburger)
        toolbar.setNavigationOnClickListener {
            toggle()
        }
    }
    private fun toggle()
    {
        val drawerLayout: DrawerLayout = findViewById (R.id.drawer_layout)
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}