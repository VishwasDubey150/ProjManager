package com.example.projmanager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projmanager.Activities.base_activity
import com.example.projmanager.Activities.create_board
import com.example.projmanager.Activities.login
import com.example.projmanager.Activities.profile_activity
import com.example.projmanager.Firebase.FirestoreClass
import com.example.projmanager.adapter.BoardsItemAdapter
import com.example.projmanager.models.Board
import com.example.projmanager.models.User
import com.example.projmanager.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class home_activity : base_activity()  {

    companion object{
        const val REQUEST_CODE : Int = 11
        const val CREATE_BOARD_REQUEST_CODE: Int=12
    }

    private lateinit var mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupActionBar()
        FirestoreClass().LoadUserData(this,true)
    }

    fun updateNavUserDetail(user:User,readBoardsList : Boolean)
    {
        mUserName=user.name
        val nav_user_image=findViewById<ImageView>(R.id.nav_user_image)
        val text_name=findViewById<TextView>(R.id.name_text)
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(nav_user_image)

        text_name.text=user.name

        if(readBoardsList)
        {
            showPD()
            FirestoreClass().getBoardsList(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE)
        {
            FirestoreClass().LoadUserData(this)
        }

        else if(resultCode == Activity.RESULT_OK && requestCode == CREATE_BOARD_REQUEST_CODE)
        {
            FirestoreClass().getBoardsList(this)
        }
        else
        {
            Log.e("Cancelled","Cancelled")
        }
    }

    fun logout(item: MenuItem) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, login::class.java))
        finish()
    }

    fun profile(item: MenuItem) {
        startActivityForResult(Intent(this, profile_activity::class.java), REQUEST_CODE)
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
    fun add(view: View) {
        val intent = Intent(this,create_board::class.java)
        intent.putExtra(Constants.NAME,mUserName)
        startActivityForResult(intent, CREATE_BOARD_REQUEST_CODE)
    }

    fun populateBoardsListToUI(boardsList: ArrayList<Board>)
    {
        val rv=findViewById<RecyclerView>(R.id.rv)
        val tv_no_board=findViewById<TextView>(R.id.no_board_tv)
        hidePD()

        if(boardsList.size>0)
        {
            rv.visibility=View.VISIBLE
            tv_no_board.visibility=View.GONE

            rv.layoutManager=LinearLayoutManager(this)
            rv.setHasFixedSize(true)

            val adapter=BoardsItemAdapter(this,boardsList)
            rv.adapter=adapter
        }
        else
        {
            rv.visibility=View.GONE
            tv_no_board.visibility=View.VISIBLE
        }
    }
}