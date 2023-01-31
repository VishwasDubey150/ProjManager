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
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.projmanager.Firebase.FirestoreClass
import com.example.projmanager.R
import com.example.projmanager.home_activity
import com.example.projmanager.models.User
import com.example.projmanager.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class profile_activity : base_activity() {

    companion object {
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }

    private var mselectedImageFileUri: Uri? = null
    private lateinit var mUserDetail: User
    private var mProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        FirestoreClass().LoadUserData(this)
        val iv_profile_user_image = findViewById<ImageView>(R.id.iv_profile_user_image)
        iv_profile_user_image.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
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
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val profile = findViewById<ImageView>(R.id.iv_profile_user_image)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
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


    fun back(view: View) {
        val intent = Intent(this, home_activity::class.java)
        startActivity(intent)
        finish()
    }

    fun setUSerDataInUI(user: User) {
        val iv_user_image = findViewById<ImageView>(R.id.iv_profile_user_image)
        val iv_username = findViewById<EditText>(R.id.iv_username)
        val iv_email = findViewById<EditText>(R.id.iv_email)
        val iv_moblie = findViewById<EditText>(R.id.iv_mobile)
        mUserDetail=user
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(iv_user_image)

        iv_username.setText(user.name)
        iv_email.setText(user.email)
        if (user.mobile != 0L) {
            iv_moblie.setText(user.mobile.toString())
        }
    }

    private fun updateUserProfileData() {
        val iv_user_image = findViewById<ImageView>(R.id.iv_profile_user_image)
        val iv_username = findViewById<EditText>(R.id.iv_username)
        val iv_email = findViewById<EditText>(R.id.iv_email)
        val iv_moblie = findViewById<EditText>(R.id.iv_mobile)

        val userHashMap = HashMap<String, Any>()


        if (mProfileImageURL.isNotEmpty() && mProfileImageURL != mUserDetail.image) {
            userHashMap[Constants.IMAGE] = mProfileImageURL
        }

        if (iv_username.text.toString() != mUserDetail.name) {
            userHashMap[Constants.NAME] = iv_username.text.toString()
        }

        if (iv_moblie.text.toString() != mUserDetail.mobile.toString()) {
            userHashMap[Constants.MOBILE] = iv_moblie.text.toString().toLong()
        }
        FirestoreClass().updateUserProfileData(this, userHashMap)


    }

    private fun uploadUserImage() {
        showPD()
        if (mselectedImageFileUri != null) {
            val sRef: StorageReference =
                FirebaseStorage.getInstance().reference.child("USER_IMAGE" + System.currentTimeMillis()
                        + "." + getExtension(mselectedImageFileUri))

            sRef.putFile(mselectedImageFileUri!!).addOnSuccessListener { taskSnapshot ->
                Log.i("URL", taskSnapshot.metadata!!.reference!!.downloadUrl.toString())
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    Log.i("Downloadable Image URL", uri.toString())
                    mProfileImageURL = uri.toString()
                    updateUserProfileData()
                }
            }.addOnFailureListener { exceptiion ->
                Toast.makeText(this, exceptiion.message, Toast.LENGTH_SHORT).show()
                hidePD()
            }
        }
    }


    private fun getExtension(uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(contentResolver.getType(uri!!))
    }


    fun update(view: View) {
        if (mselectedImageFileUri != null) {
            uploadUserImage()
        } else {
            showPD()
            updateUserProfileData()
        }
    }

        fun profileupdatedSuccess() {
            hidePD()
            finish()
        }
    }
