package com.example.projmanager.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.projmanager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Signup : base_activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
    }

    fun back(view: View) {
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun sign_up(view: View) {
        var et_name=findViewById<EditText>(R.id.name)
        var et_email=findViewById<EditText>(R.id.email)
        var et_password=findViewById<EditText>(R.id.password)
        val name: String=et_name.text.toString()
        val email:String=et_email.text.toString().trim{it<=' '}
        val password: String=et_password.text.toString()

        if(validateform(name,email,password))
            {
                showPD()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    hidePD()

                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        Toast.makeText(this,
                            "$name your accout is created successfully!!",
                            Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun validateform(name: String,email:String,password:String):Boolean {
        return when
        {
            TextUtils.isEmpty(name.toString())-> {
                Toast.makeText(this,"Please enter the name",Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(email.toString())-> {
                Toast.makeText(this,"Please enter the email",Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(password.toString())-> {
                Toast.makeText(this,"Please enter the Password",Toast.LENGTH_SHORT).show()
                false
            }

            else ->
            {
                true
            }
        }
    }
}