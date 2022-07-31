package com.doubleclick.widdingkmm.android.ui.Profile

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.doubleclick.widdingkmm.android.Model.Constants
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Repository.BaseRepository
import com.doubleclick.widdingkmm.android.ViewModel.PostsViewModel
import com.doubleclick.widdingkmm.android.ViewModel.UserViewModel
import com.doubleclick.widdingkmm.android.Views.CircleImageView
import com.doubleclick.widdings.Adapters.PostsAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class ProfileActivity : AppCompatActivity() {
    private lateinit var name: TextView;
    private lateinit var nameSmall: TextView;
    private lateinit var description: TextView;
    private lateinit var appbar: AppBarLayout;
    private lateinit var imageProfile: CircleImageView;
    private lateinit var back: ImageView;
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var toolbar: Toolbar;
    private lateinit var reference: DatabaseReference
    private lateinit var userViewModel: UserViewModel;
    private val REQUEST_CODE: Int = 100;
    private var type: String = "";
    private lateinit var create_post: LinearLayout
    private lateinit var postsViewModel: PostsViewModel;
    private lateinit var myPosts: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        reference = FirebaseDatabase.getInstance().reference;
        back = findViewById(R.id.back);
        imageProfile = findViewById(R.id.imageProfile)
        appbar = findViewById(R.id.appbar)
        nameSmall = findViewById(R.id.nameSmall);
        description = findViewById(R.id.description);
        create_post = findViewById(R.id.create_post);
        name = findViewById(R.id.name);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        myPosts = findViewById(R.id.myPosts);
        postsViewModel = ViewModelProvider(this)[PostsViewModel::class.java];
//        group_dummy = findViewById(R.id.group_dummy);
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setSupportActionBar(toolbar)

        back.setOnClickListener {
            startActivity(Intent(this, ImagesActivity::class.java))
            finish()
        }
        userViewModel.getUserDate().observe(this) {
            name.text = it.name
            nameSmall.text = it.name
            description.text = it.description;
            Glide.with(this).load(it.image).into(imageProfile)
            // reference -> https://www.tutorialspoint.com/how-does-one-use-glide-to-download-an-image-into-a-bitmap
            Glide.with(this).asDrawable().load(it.cover)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        appbar.background = resource
                    }
                })
        }

        postsViewModel.getLiveMyPosts(FirebaseAuth.getInstance().currentUser!!.uid.toString())
            .observe(this) {
                myPosts.adapter = PostsAdapter(this, it);
            }


        create_post.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java));
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.description -> edit("description");
            R.id.editname -> edit("name")
            R.id.editPhone -> edit("phone")
            R.id.editcover -> selectType("cover")
            R.id.editProfile -> selectType("image")
            R.id.location -> editLocation()
            R.id.join -> ""
        }

        return super.onOptionsItemSelected(item)
    }

    private fun selectType(type: String) {
        this.type = type;
        openFiles();
    }

    private fun editLocation() {

    }

    private fun edit(type: String) {
        val bottomSheetEditor = BottomSheetEditor(BaseRepository().id, type);
        bottomSheetEditor.show(supportFragmentManager, "Edit")
    }

    private fun openFiles() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            uploadImage(data.data!!)
        }
    }

    private fun uploadImage(data: Uri) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        if (data.toString() != "") {
            val fileReference =
                FirebaseStorage.getInstance().reference.child("ProfileImages").child(
                    System.currentTimeMillis().toString() + "." + getFileExtension(data)
                )
            fileReference.putFile(data)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                    val uri =
                        taskSnapshot.storage.downloadUrl
                    uri.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val map =
                                HashMap<String, Any>()
                            map[type] = task.result.toString()
                            reference.child(Constants.DBUsers).child(BaseRepository().id)
                                .updateChildren(map)
                            progressDialog.dismiss()
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

    fun getFileExtension(uri: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

}