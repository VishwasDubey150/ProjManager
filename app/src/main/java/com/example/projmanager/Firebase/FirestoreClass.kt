package com.example.projmanager.Firebase
import android.app.Activity
import com.example.projmanager.Activities.Signup
import com.example.projmanager.Activities.profile_activity
import com.example.projmanager.home_activity
import com.example.projmanager.models.User
import com.example.projmanager.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun LoadUserData(activity: Activity) {
        mFirestore.collection(Constants.USERS)
            .document(getCurrnetUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)!!

                when (activity) {
                    is Signup -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is home_activity -> {
                        activity.updateNavUserDetail(loggedInUser)
                    }
                    is profile_activity -> {
                        activity.setUSerDataInUI(loggedInUser)

                    }
                }
            }
    }


    fun registerUser(activity: Signup,UserInfo: User)
    {
        mFirestore.collection(Constants.USERS)
            .document(getCurrnetUserId())
            .set(UserInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
    }

    fun getCurrnetUserId(): String{

        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser!=null)
        {
            currentUserId=currentUser.uid
        }
        return currentUserId
        }
}