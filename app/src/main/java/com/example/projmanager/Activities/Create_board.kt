package com.example.projmanager.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.projmanager.Activities.create_board.Companion.READ_STORAGE_PERMISSION_CODE
import com.example.projmanager.Firebase.FirestoreClass
import com.example.projmanager.R
import com.example.projmanager.home_activity
import com.example.projmanager.models.Board
import com.example.projmanager.models.User
import com.example.projmanager.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class create_board : base_activity() {

    companion object {
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }

    private var mselectedImageFileUri: Uri? = null
    private lateinit var mUserName: String
    private var mBoardURL :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)
        supportActionBar?.hide()

        val created=findViewById<Button>(R.id.create)

        created.setOnClickListener {
            if(mselectedImageFileUri != null)
            {
                uploadBoardImage()
            }
            else
            {
                showPD()
                createBoard()
            }
        }

        if(intent.hasExtra(Constants.NAME))
        {
            mUserName= intent.getStringExtra(Constants.NAME)!!
        }

        var image=findViewById<ImageView>(R.id.iv_profile_user_image)

        image.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    create_board.READ_STORAGE_PERMISSION_CODE)
            }
        }
    }

    private fun createBoard()
    {
        var board_image=findViewById<ImageView>(R.id.iv_profile_user_image)
        var board_name=findViewById<EditText>(R.id.board_name)
        var create=findViewById<Button>(R.id.create)
        val assignedUsersArrayList: ArrayList<String> = ArrayList()
        assignedUsersArrayList.add(getCurrentUserID())

        var  board=Board(board_name.text.toString(),mBoardURL,mUserName,assignedUsersArrayList)

        FirestoreClass().createBoard(this,board)
    }

    private fun getExtension(uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    private fun uploadBoardImage()
    {
        showPD()
        if (mselectedImageFileUri != null) {
            val sRef: StorageReference =
                FirebaseStorage.getInstance().reference.child("BOARD_IMAGE" + System.currentTimeMillis()
                        + "." + getExtension(mselectedImageFileUri))

            sRef.putFile(mselectedImageFileUri!!).addOnSuccessListener { taskSnapshot ->
                Log.i("Board Image URL", taskSnapshot.metadata!!.reference!!.downloadUrl.toString())
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    Log.i("Downloadable Image URL", uri.toString())
                    mBoardURL = uri.toString()
                    createBoard()
                }
            }.addOnFailureListener { exceptiion ->
                Toast.makeText(this, exceptiion.message, Toast.LENGTH_SHORT).show()
                hidePD()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == create_board.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser(this)

            } else {
                Toast.makeText(
                    this, "You Denied",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, create_board.PICK_IMAGE_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val profile = findViewById<ImageView>(R.id.iv_profile_user_image)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == create_board.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        mselectedImageFileUri = data.data!!
                        Glide
                            .with(this)
                            .load(mselectedImageFileUri)
                            .centerCrop()
                            .placeholder(R.drawable.profile)
                            .into(profile)

                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun backC(view: View) {
        startActivity(Intent(this,home_activity::class.java))
    }

    fun boardCreatedSuccessfully()
    {
        hidePD()
        setResult(Activity.RESULT_OK)
        finish()
    }
}