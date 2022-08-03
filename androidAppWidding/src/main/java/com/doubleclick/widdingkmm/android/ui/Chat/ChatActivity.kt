package com.doubleclick.widdingkmm.android.ui.Chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.databasekmm.android.MessageDatabase
import com.doubleclick.widdingkmm.android.Model.*
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewModel.UserViewModel
import com.doubleclick.widdingkmm.android.Views.CircleImageView
import com.doubleclick.widdingkmm.android.Views.SwipeToReply.ISwipeControllerActions
import com.doubleclick.widdingkmm.android.Views.SwipeToReply.SwipeController
import com.doubleclick.widdingkmm.android.Views.audio_record_view.AttachmentOption
import com.doubleclick.widdingkmm.android.Views.audio_record_view.AttachmentOptionsListener
import com.doubleclick.widdingkmm.android.Views.audio_record_view.AudioRecordView
import com.doubleclick.widdingkmm.android.Views.audio_record_view.RecordingListener
import com.doubleclick.widdingkmm.android.`interface`.APIService
import com.doubleclick.widdingkmm.android.`interface`.OnMessageClick
import com.doubleclick.widdings.Adapters.BaseMessageAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.google.gson.Gson
import com.iceteck.silicompressorr.SiliCompressor
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.android.synthetic.main.record_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import persondata.MessagesEntity
import persondata.MessagesEntityQueries
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.HashMap

class ChatActivity : AppCompatActivity(), AttachmentOptionsListener, RecordingListener,
    OnMessageClick, ChildEventListener {

    private lateinit var audioRecordView: AudioRecordView
    private lateinit var containerView: View
    private lateinit var reference: DatabaseReference
    private lateinit var userId: String
    private lateinit var myId: String
    private lateinit var apiService: APIService
    private lateinit var user: User;
    private var postData: PostModelData? = null
    private lateinit var baseMessageAdapter: BaseMessageAdapter
    private lateinit var userViewModel: UserViewModel
    private var messageModelsList: MutableList<MessageModel> = ArrayList();
    private var reply: String? = ""
    private lateinit var chatRecycler: RecyclerView;
    private lateinit var profile_image: CircleImageView;
    private lateinit var username: TextView
    private lateinit var mediaRecorder: MediaRecorder
    var audioPath: String? = null
    var client: FusedLocationProviderClient? = null
    var fileType: String? = null
    private val REQUEST_CODE = 100;
    private lateinit var storageReference: StorageReference
    lateinit var uploadTask: StorageTask<UploadTask.TaskSnapshot>
    private lateinit var androidSqlDriver: AndroidSqliteDriver
    private lateinit var queries: MessagesEntityQueries;

//    private lateinit var chatListViewModelDatabase: ChatListViewModelDatabase;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        apiService =
            Client.getClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)
        client = LocationServices.getFusedLocationProviderClient(this)
        reference = FirebaseDatabase.getInstance().reference
        reference.keepSynced(true)
        myId = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        userId = intent.getStringExtra("userId").toString()
        reference.child(Constants.DBCHATS).child(myId).child(userId).addChildEventListener(this)
        audioRecordView = AudioRecordView();
        audioRecordView.initView(findViewById<View>(R.id.root_view) as FrameLayout /* must be -> FrameLayout */)
        containerView = audioRecordView.setContainerView(R.layout.layout_chatting)!!
        chatRecycler = containerView.findViewById(R.id.chatRecycler);
        username = containerView.findViewById(R.id.username);
        profile_image = containerView.findViewById(R.id.profile_image);
        androidSqlDriver = AndroidSqliteDriver(
            schema = MessageDatabase.Schema,
            context = this@ChatActivity,
            name = "message.db"
        )
        queries = MessageDatabase(androidSqlDriver).messagesEntityQueries

//        chatListViewModelDatabase = ViewModelProvider(this)[ChatListViewModelDatabase::class.java]
//        messageModelsList.addAll(chatListViewModelDatabase.getListData(myId, userId));
        audioRecordView.setRecordingListener(this)
        audioRecordView.getMessageView()!!.requestFocus()
        audioRecordView.setAttachmentOptions(AttachmentOption.defaultList, this)
        audioRecordView.removeAttachmentOptionAnimation(false)
        baseMessageAdapter = BaseMessageAdapter(messageModelsList, this, this, myId);
        chatRecycler.adapter = baseMessageAdapter;
        setListener()
        setReplay();
        userViewModel.getUserById(userId).observe(this) {
            user = it;
            Glide.with(this).load(it.image).into(profile_image);
            username.text = it.name
            Log.e("GGGGGGGGGG", it.toString())
        }
//        chatListViewModelDatabase.getAllData().observe(this@ChatActivity) {
//            Log.e("CHATDATA", it.toString());
//        }

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {

            }
        }
        try {
            postData = intent.getSerializableExtra("postData") as PostModelData?
            if (postData != null) {
                val map: HashMap<String, Any> = HashMap();
                map["sender"] = myId
                map["receiver"] = userId
                map["message"] = postData!!.postModel.images
                map["type"] = Constants.SHARED_POST
                map["id"] = postData!!.postModel.id
                map["time"] = Date().time.toString();
                map["uri"] = ""
                map["seen"] = "false"
                map["reply"] = postData!!.postModel.caption
                upload(postData!!.postModel.id, map);
                makeChatList()
            }
        } catch (e: NullPointerException) {

        }
    }

    private fun setReplay() {
        val swipeController = SwipeController(this, object : ISwipeControllerActions {
            override fun onSwipePerformed(position: Int) {
                reply = messageModelsList[position].message.toString();
            }
        })
        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(chatRecycler)
    }

    fun getFileExtension(uri: Uri?): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    fun sendFileData(uri: Uri) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.show()
        if (uri.toString() != "") {
            storageReference =
                FirebaseStorage.getInstance().getReference("/ChatData/Files")
            val fileReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + getFileExtension(uri)
            )
            uploadTask = fileReference.putFile(uri).addOnSuccessListener {
                val url = it.storage.downloadUrl
                url.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val url = it.result.toString()
                        val map = HashMap<String, Any>()
                        val time = Date().time;
                        val id = reference.push().key.toString() + time
                        map["sender"] = myId
                        map["receiver"] = userId
                        map["message"] = url
                        map["type"] = fileType.toString()
                        map["id"] = id
                        map["seen"] = "false"
                        map["time"] = time.toString()
                        map["uri"] = uri.toString()
                        if (!reply.equals("")) {
                            map["reply"] = reply.toString()
                        }
                        upload(id, map);
                        progressDialog.dismiss()
                        makeChatList()
                        sendNotifiaction(fileType.toString())
                    } else {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                }
            }.addOnProgressListener {
                val p: Double =
                    100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("${p.toInt()} % Uploading...")
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            var filePath: String? = "";
            if (fileType.equals("image")) {
                filePath = SiliCompressor.with(this).compress(
                    data.data.toString(),
                    File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + "/MarketEslam/Images/"
                    )
                )
            }
            if (fileType.equals("video")) {
                sendFileData(data.data!!)
            }
            sendFileData(Uri.parse(filePath))
        }
    }

    override fun onClick(attachmentOption: AttachmentOption) {
        when (attachmentOption!!.getId()) {
            AttachmentOption.VIDEO_ID -> sendVideo();
            AttachmentOption.GALLERY_ID -> sendImage();
            AttachmentOption.LOCATION_ID -> getMyLocation()
        }
    }


    private fun sendImage() {
        fileType = "image";
        openFiles("image/*");
    }

    private fun sendVideo() {
        fileType = "video"
        openFiles("video/*")
    }

    private fun openFiles(mimeType: String) {
        val intent = Intent()
        intent.type = mimeType
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_CODE)
    }


    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val task = client!!.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    val uri = "[" + location.latitude + "," + location.longitude + "]"
                    sentMessage(uri, "location")
                } else {
                    Toast.makeText(this, "Open your location", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setUpRecordingLocal() {
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        val file =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/MarketEslam/Recording/") // String f = "/storage/emulated/0/Download";
        if (!file.exists()) {
            file.mkdirs()
        }
        val f = File(file, "chat" + Date().time + ".mp3")
        audioPath =
            f.path //file.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".3gp";
        mediaRecorder.setOutputFile(f.path)
    }

    private fun sendRecodingMessage(audioPath: String?) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.show()
        if (audioPath != null) {
            val storageReference = FirebaseStorage.getInstance()
                .getReference("/ChatData/Recording/" + myId + ":" + userId.toString() + System.currentTimeMillis())
            Log.e("audio path", audioPath)
            val audioFile = Uri.fromFile(File(audioPath))
            Log.e("audioFile = ", audioFile.toString())
            storageReference.putFile(audioFile)
                .addOnSuccessListener { success: UploadTask.TaskSnapshot ->
                    val audioUrl = success.storage.downloadUrl
                    audioUrl.addOnCompleteListener { path: Task<Uri> ->
                        if (path.isSuccessful) {
                            val url = path.result.toString()
                            val map: HashMap<String, Any> = HashMap()
                            val time = Date().time
                            val id = reference.push().key.toString() + time;
                            map["sender"] = myId
                            map["receiver"] = userId.toString()
                            map["message"] = url
                            map["type"] = "voice"
                            map["id"] = id
                            map["time"] = time.toString()
                            map["seen"] = "false"
                            map["uri"] = audioPath;
                            if (!reply.equals("")) {
                                map["reply"] = reply.toString()
                            }
                            upload(id, map);
                            progressDialog.dismiss()
                            makeChatList()
                            sendNotifiaction("audio")
                        } else {
                            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
                }.addOnProgressListener {
                    val p: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("${p.toInt()} % Uploading...")
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }

        }
    }

    override fun onRecordingStarted() {
        setUpRecordingLocal();
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onRecordingLocked() {
        Toast.makeText(this, "onRecordingLocked", Toast.LENGTH_SHORT).show()
    }

    override fun onRecordingCompleted() {
        sendRecodingMessage(audioPath)
        try {
            mediaRecorder.stop()
            mediaRecorder.release()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onRecordingCanceled() {
        mediaRecorder.reset()
        mediaRecorder.release()
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setListener() {
        var cklicked = true
        audioRecordView.getEmojiView().setOnClickListener {
            audioRecordView.hideAttachmentOptionView()
            if (cklicked) {
                audioRecordView.getEmojiPopup().toggle()
                cklicked = false
                audioRecordView.getEmojiView().imageViewEmoji.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.keyboard
                    )
                )
            } else {
                audioRecordView.getEmojiPopup().dismiss()
                cklicked = true
                audioRecordView.getEmojiView().imageViewEmoji.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_baseline_insert_emoticon_24
                    )
                )
            }
        }

        audioRecordView.getSendView().setOnClickListener {
            sentMessage(audioRecordView.getMessageView().text.toString(), Constants.TEXT);
        }
    }

    private fun sentMessage(text: String, type: String) {
        val map: HashMap<String, Any> = HashMap()
        val time = Date().time;
        val id = reference.push().key.toString() + time;
        map["sender"] = myId
        map["message"] = text
        map["type"] = type
        map["receiver"] = userId // Id of Friend
        map["time"] = time.toString()
        map["id"] = id
        map["seen"] = "false"
//        map["reply"] = reply.toString()
        upload(id, map);
        audioRecordView.getMessageView().setText("")
        makeChatList();
        sendNotifiaction(text);
    }

    /*
    * TODO => to  makeChatList
    * */
    private fun makeChatList() {
        val map1: HashMap<String, Any> = HashMap();
        map1["id"] = userId
        map1["time"] = -1 * Date().time;
        reference.child(Constants.DBChatList).child(myId).child(userId).updateChildren(map1)
        val map2: HashMap<String, Any> = HashMap();
        map2["id"] = myId
        map2["time"] = -1 * Date().time;
        reference.child(Constants.DBChatList).child(userId).child(myId).updateChildren(map2)

    }

    /*
     * TODO => to upload message
     * */
    private fun upload(id: String, map: HashMap<String, Any>) {
        reference.child(Constants.DBCHATS).child(myId).child(userId.toString())
            .child(id).updateChildren(map);
        map.remove("uri");
        reference.child(Constants.DBCHATS).child(userId.toString()).child(myId)
            .child(id).updateChildren(map);
    }

    /*
    * TODO => for sendNotifiaction
    * */
    private fun sendNotifiaction(message: String) {
        val data = Data(
            myId,
            R.drawable.download,
            "${user!!.name.toString()}: $message",
            "New Message",
            userId.toString()
        )
        val sender = Sender(data, user!!.token.toString())
        apiService.sendNotification(sender)
            .enqueue(object : Callback<MyResponse> {
                override fun onResponse(
                    call: Call<MyResponse>,
                    response: Response<MyResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()!!.success != 1) {
                            Toast.makeText(this@ChatActivity, "Failed!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
            })
    }

    override fun download(messageModel: MessageModel, pos: Int) {
//        var FileDownloadUrlConnection = FileDownloadUrlConnection(messageModel.message)

    }

    override fun deleteForMe(messageModel: MessageModel, pos: Int) {
        messageModelsList.removeAt(pos);
        reference.child(Constants.DBCHATS).child(myId).child(userId)
            .child(messageModel.id.toString())
            .removeValue()
    }

    override fun deleteForAll(messageModel: MessageModel, pos: Int) {
        reference.child(Constants.DBCHATS).child(myId).child(userId)
            .child(messageModel.id.toString())
            .removeValue().addOnCompleteListener {
                reference.child(Constants.DBCHATS).child(userId).child(myId)
                    .child(messageModel.id.toString())
                    .removeValue().addOnCompleteListener {
                        messageModelsList.removeAt(pos);
                    }
            }

    }

    override fun replyIndex(messageModel: MessageModel, pos: Int) {
        chatRecycler.smoothScrollToPosition(
            messageModelsList.indexOf(messageModel)
        )
    }

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val gson = Gson()
        val json = Gson().toJson(snapshot.value)
        val data = gson.fromJson(json, MessageModel::class.java)
        Log.e("DATAMODEL", data.toString());
        val messageModel = MessageModel(
            data.id,
            data.message,
            data.type,
            data.sender,
            data.receiver,
            data.time,
            data.seen,
            data.reply,
            data.uri
        )
        queries.insertMessageById(
            data.id,
            data.message,
            data.type,
            data.sender,
            data.receiver,
            data.time.toString(),
            "true",
            data.reply,
            data.uri
        )
        val itemsBefore = queries.getAllMessage().executeAsList()
        messageModelsList.addAll(queries.getAllMessage().executeAsList())
        Log.e("DATAMESSAGE", itemsBefore.toString());
        baseMessageAdapter.notifyDataSetChanged()
//        chatListViewModelDatabase.insert(
//            messageModel
//        )
        if (data.seen == "false" && data.sender != myId && data.receiver == userId) {
//            chatListViewModelDatabase.insert(
//                messageModel
//            )
        }

        if (!messageModel.message.contains("@$@this@message@deleted")) {
            try {
                /*
                * work when i closed connection and open again
                * it's see what message is deleted and updated it in database and in ArrayList
                * and if message sent and deleted before stored in database -> exception
                * */
                if (messageModel.sender == myId /*&& !messageModel.seen*/ && !messageModel.message.contains(
                        "@$@this@message@deleted"
                    )
                ) {
                    if (!messageModelsList.contains(messageModel)) {
                        messageModelsList.add(messageModel)
                        baseMessageAdapter.notifyItemInserted(messageModelsList.size - 1)
                        baseMessageAdapter.notifyDataSetChanged()
                        chatRecycler.scrollToPosition(messageModelsList.size - 1)
                        chatRecycler.smoothScrollToPosition(messageModelsList.size - 1)
                    }
                }
                /*
                * if I'm receiver -> update that i see this message
                * */
                if (messageModel.receiver == myId) {
                    // TODO update status massage to been seen
                    val map: HashMap<String, Any> = HashMap();
                    map["seen"] = true
                    reference.child(Constants.DBCHATS).child(myId).child(userId)
                        .child(messageModel.id)
                        .updateChildren(map);
                    reference.child(Constants.DBCHATS).child(userId).child(myId)
                        .child(messageModel.id)
                        .updateChildren(map);
                }
            } catch (e: Exception) {

            }
        }
        if (messageModel.message.contains("@$@this@message@deleted")) {
            try {
                val c = messageModel;
                c!!.message = "this message deleted";
                c.type = "text"
//                chatListViewModelDatabase.update(c!!)
                messageModelsList[messageModelsList.indexOf(c)] = c
                baseMessageAdapter.notifyItemChanged(messageModelsList.indexOf(c))
                baseMessageAdapter.notifyDataSetChanged()
            } catch (e: IOException) {
                Log.e("DatabaseExption216", e.message.toString());
            } catch (e: IndexOutOfBoundsException) {
                Log.e("ArrayListExp218", e.message.toString());
            } catch (e: Exception) {
                Log.e("Exception220", e.message.toString());
            }
        }
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val gson = Gson()
        val json = gson.toJson(snapshot.value)
        val data = gson.fromJson(json, MessageModel::class.java)
        val messageModel = MessageModel(
            data.id,
            data.message,
            data.type,
            data.sender,
            data.receiver,
            data.time,
            data.seen,
            data.reply,
            data.uri
        )
//        chatListViewModelDatabase.update(messageModel!!)

    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        val gson = Gson()
        val json = gson.toJson(snapshot.value)
        val data = gson.fromJson(json, MessageModel::class.java)
        val messageModel = MessageModel(
            data.id,
            data.message,
            data.type,
            data.sender,
            data.receiver,
            data.time,
            data.seen,
            data.reply,
            data.uri
        )
//        chatListViewModelDatabase.delete(messageModel!!)
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

    }

    override fun onCancelled(error: DatabaseError) {

    }

    private fun <E> MutableList<E>.addAll(elements: List<MessagesEntity>) {
        for (element in elements) {
            messageModelsList.add(
                MessageModel(
                    element.id,
                    element.message,
                    element.type,
                    element.sender,
                    element.receiver_,
                    element.time,
                    element.seen,
                    element.reply,
                    element.uri
                )
            )
        }
    }
}


