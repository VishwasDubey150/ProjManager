package com.example.projmanager.Activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projmanager.R
import com.google.firebase.auth.FirebaseAuth

open class base_activity : AppCompatActivity() {

    private lateinit var mprogress:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showPD()
    {
        mprogress= Dialog(this)
        mprogress.setContentView(R.layout.dialog_progress)
        mprogress.show()
    }

    fun hidePD()
    {   mprogress.hide()
    }

    fun getCurrentUserID():String
    {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}