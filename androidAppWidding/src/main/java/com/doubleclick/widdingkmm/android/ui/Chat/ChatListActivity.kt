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
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import persondata.ChatListQueries
import persondata.MessagesEntityQueries
import persondata.UserEntityQueries

class ChatListActivity : AppCompatActivity() {

    private lateinit var chatlist: RecyclerView
    private lateinit var chatListAdapter: ChatListAdapter;
    private lateinit var chatListViewModel: ChatListViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        chatlist = findViewById(R.id.chatlist);
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



        }
    }
}