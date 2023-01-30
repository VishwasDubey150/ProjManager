package com.example.projmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.projmanager.Activities.login
import com.example.projmanager.Activities.profile_activity
import com.example.projmanager.R
import com.google.firebase.auth.FirebaseAuth
import io.grpc.InternalChannelz.id

class home_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupActionBar()
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