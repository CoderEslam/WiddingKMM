package com.doubleclick.widdingkmm.android.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.Model.User
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.CircleImageView

/**
 * Created By Eslam Ghazy on 7/14/2022
 */
class ChatListAdapter(private val users: List<User>?) :
    RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val circleImageView: CircleImageView = itemView.findViewById(R.id.circleImageView);
        val name: TextView = itemView.findViewById(R.id.name);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_list_layout, parent, false)
        );
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
//        Glide.with(holder.itemView.context).load(users!![position].image)
//            .into(holder.circleImageView);
        holder.name.text = users!![position].name;
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Done" + position, Toast.LENGTH_SHORT).show();
//            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
//            intent.putExtra("userId", users[position].id);
//            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return users!!.size;
    }
}