package com.example.projmanager.Firebase
import android.app.Activity
import android.provider.ContactsContract.Profile
import android.util.Log
import android.widget.Toast
import com.example.projmanager.Activities.MainActivity
import com.example.projmanager.Activities.Signup
import com.example.projmanager.Activities.create_board
import com.example.projmanager.Activities.profile_activity
import com.example.projmanager.home_activity
import com.example.projmanager.models.Board
import com.example.projmanager.models.User
import com.example.projmanager.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject

class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun LoadUserData(activity: Activity,readBoardsList :Boolean=false) {
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
                        activity.updateNavUserDetail(loggedInUser,readBoardsList)
                    }
                    is profile_activity -> {
                        activity.setUSerDataInUI(loggedInUser)

                    }
                }
            }
    }

    fun createBoard(activity: create_board, board: Board)
    {
        mFirestore.collection(Constants.BOARDS)
            .document()
            .set(board, SetOptions.merge())
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName,"Board created successfully")
                Toast.makeText(activity,"Boarrd created successfully.",Toast.LENGTH_SHORT).show()
                activity.boardCreatedSuccessfully()
            }.addOnFailureListener {
                exception ->
                activity.hidePD()
                Log.e(activity.javaClass.simpleName,"Error",exception)
            }
    }

    fun getBoardsList(activity: home_activity)
    {
        mFirestore.collection(Constants.BOARDS)
            .whereArrayContains(Constants.ASSIGNED_TO,getCurrnetUserId())

            .get()
            .addOnSuccessListener {
                document ->
                Log.i(activity.javaClass.simpleName,document.documents.toString())
                val boardList: ArrayList<Board> = ArrayList()
                for(i in document.documents)
                {
                    val board=i.toObject(Board::class.java)!!
                    board.documentId=i.id
                    boardList.add(board)
                }

                activity.populateBoardsListToUI(boardList)
            }.addOnFailureListener { e ->

                activity.hidePD()
                Log.e(activity.javaClass.simpleName,"Error")
            }
    }

    fun updateUserProfileData(activity : profile_activity,userHashMap: HashMap<String, Any>)
    {
        mFirestore.collection(Constants.USERS)
            .document(getCurrnetUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName,"Profile data updated")
                Toast.makeText(activity,"Profile updated successfully!",Toast.LENGTH_SHORT).show()
                activity.profileupdatedSuccess()
            }.addOnFailureListener {
                e ->
                activity.hidePD()
                Log.e(activity.javaClass.simpleName,"Error",e)
                Toast.makeText(activity,"Error while updating",Toast.LENGTH_SHORT).show()
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