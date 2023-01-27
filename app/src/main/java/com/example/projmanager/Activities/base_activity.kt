package com.example.projmanager.Activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import com.example.projmanager.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class base_activity : AppCompatActivity() {

    private lateinit var mprogress:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showPD(text: String)
    {
        mprogress= Dialog(this)
        mprogress.setContentView(R.layout.dialog_progress)
        mprogress.show()
    }

    fun getCurrentUserID():String
    {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}