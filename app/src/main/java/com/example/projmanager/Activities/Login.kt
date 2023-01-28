package com.example.projmanager.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.projmanager.R
import com.example.projmanager.home_activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class login : base_activity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        auth = Firebase.auth

    }
    fun login(view: View) {
        var emailL=findViewById<EditText>(R.id.emailL)
        var passwordL=findViewById<EditText>(R.id.passwordL)

        val email=emailL.text.toString()
        val password=passwordL.text.toString()
        if(validateform(email,password))
        {
            showPD()
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    hidePD()
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                        val intent=Intent(this, home_activity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun validateform(email:String,password:String):Boolean {
        return when
        {
            TextUtils.isEmpty(email.toString())-> {
                Toast.makeText(this,"Please enter the email", Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(password.toString())-> {
                Toast.makeText(this,"Please enter the Password", Toast.LENGTH_SHORT).show()
                false
            }
            else ->
            {
                true
            }
        }
    }


}