package com.doubleclick.widdingkmm.android.ui.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.databasekmm.android.MessageDatabase
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewModel.ChatListViewModel
import com.doubleclick.widdings.Adapters.ChatListAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import persondata.MessagesEntityQueries
import persondata.UserEntityQueries

class ChatListActivity : AppCompatActivity() {

    private lateinit var chatlist: RecyclerView
    private lateinit var chatListAdapter: ChatListAdapter;
    private lateinit var chatListViewModel: ChatListViewModel;
    private lateinit var androidSqlDriver: AndroidSqliteDriver
    private lateinit var queries: UserEntityQueries;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        chatlist = findViewById(R.id.chatlist);
        androidSqlDriver = AndroidSqliteDriver(
            schema = MessageDatabase.Schema,
            context = this@ChatListActivity,
            name = "user.db"
        )
        queries = MessageDatabase(androidSqlDriver).userEntityQueries
        try {
            val objectChatShare =
                intent.getSerializableExtra("objectChatShare") as MessageModel
            if (objectChatShare.message != "") {

            }
        } catch (e: Exception) {

        }

        chatListViewModel = ViewModelProvider(this)[ChatListViewModel::class.java];
        chatListViewModel.getChatList().observe(this) {
            Log.e("USERGHET", it.toString());
            chatListAdapter = ChatListAdapter(it);
            chatlist.adapter = chatListAdapter;

        }


        chatListViewModel.getUserAdd().observe(this) {
            queries.insertUserById(
                it.id,
                it.name,
                it.phone,
                it.email,
                it.token,
                it.image,
                it.description,
                it.cover
            )

            Log.e("USERDATA", queries.getAllUsers().executeAsList().toString());
        }
    }
}