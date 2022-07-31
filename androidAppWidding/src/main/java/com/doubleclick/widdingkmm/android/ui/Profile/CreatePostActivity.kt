package com.doubleclick.widdingkmm.android.ui.Profile

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewModel.UserViewModel
import com.doubleclick.widdingkmm.android.Views.CircleImageView
import com.doubleclick.widdingkmm.android.Views.socialtextview.widget.SocialEditText
import com.doubleclick.widdings.Adapters.ImagesLocalAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


class CreatePostActivity : AppCompatActivity() {
    private lateinit var myImage: CircleImageView;
    private lateinit var myName: TextView;
    private lateinit var caption: SocialEditText
    private lateinit var imageRecycler: RecyclerView;
    private lateinit var postbtn: Button
    private lateinit var pickImageVideo: ConstraintLayout
    private lateinit var data: List<String>
    private var dataURL: MutableList<String> = ArrayList()
    private lateinit var reference: DatabaseReference
    private lateinit var user: User;
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        reference = FirebaseDatabase.getInstance().reference
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java];
        pickImageVideo = findViewById(R.id.pickImageVideo)
        myImage = findViewById(R.id.myImage)
        myName = findViewById(R.id.myName)
        caption = findViewById(R.id.caption)
        imageRecycler = findViewById(R.id.imageRecycler)
        postbtn = findViewById(R.id.postbtn)
        userViewModel.getUserDate().observe(this) {
            user = it;
            myName.text = it.name;
            Glide.with(this).load(it.image).into(myImage);
        }
        try {
            data = intent.getStringExtra("data").toString().replace("[", "").replace("]", "")
                .replace(" ", "").split(",");
            Log.e("DATA", data.toString());
            imageRecycler.adapter = ImagesLocalAdapter(this, data)
        } catch (e: Exception) {
        }

        postbtn.setOnClickListener {
            Post(data);
        }

        pickImageVideo.setOnClickListener {
            startActivity(Intent(this, ImagesActivity::class.java))
        }


    }

    private fun Post(data: List<String>) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        if (data.toString() != "") {
            for (uri in data) {
                val fileReference =
                    FirebaseStorage.getInstance().reference.child("PostsData").child(
                        System.currentTimeMillis()
                            .toString() + "." + getFileExtension(Uri.parse(uri))
                    )
                fileReference.putFile(Uri.parse(uri))
                    .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                        val downloadUrl = taskSnapshot.storage.downloadUrl
                        downloadUrl.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                dataURL.add(task.result.toString())
                                if (data.size == dataURL.size) {
                                    val map: HashMap<String, Any> = HashMap();
                                    val time = System.currentTimeMillis();
                                    val id = reference.push().key.toString() + time.toString()
                                    map["images"] = dataURL.toString()
                                    map["id"] = id
                                    map["userId"] = user.id.toString()
                                    map["name"] = user.name.toString()
                                    map["caption"] = caption.text.toString();
                                    map["time"] = time.toLong()
                                    map["lastModified"] = time.toLong()
                                    reference.child(Constants.DBPosts).child(id).updateChildren(map)
                                    progressDialog.dismiss()
                                }
                            } else {
                                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT)
                                    .show()
                                progressDialog.dismiss()
                            }
                        }
                    }.addOnProgressListener { snapshot ->
                        val p = 100.0 * snapshot.bytesTransferred / snapshot.totalByteCount
                        progressDialog.setMessage("$p % Uploading...")
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
            }
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }
}