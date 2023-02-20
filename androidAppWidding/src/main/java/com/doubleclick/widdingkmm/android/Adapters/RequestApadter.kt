package com.doubleclick.widdingkmm.android.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.CircleImageView


/**
 * Created By Eslam Ghazy on 7/14/2022
 */
class RequestApadter(val activity: Activity/*val requests: List<Request>*/) :
    RecyclerView.Adapter<RequestApadter.RequestViewholder>() {


    class RequestViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatLayout: LinearLayout = itemView.findViewById(R.id.chatLayout);
        val image: CircleImageView = itemView.findViewById(R.id.image);
        val name: TextView = itemView.findViewById(R.id.name);
        val recyclerImageRequest: RecyclerView =
            itemView.findViewById(R.id.recyclerImageRequest);
        val yes: Button = itemView.findViewById(R.id.yes);
        val no: Button = itemView.findViewById(R.id.no);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewholder {
        return RequestViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.request_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestViewholder, position: Int) {
//        holder.recyclerImageRequest.adapter = ImagesAdapter(activity);
        holder.chatLayout.setOnClickListener {
//            holder.itemView.context.startActivity(
//                Intent(
//                    holder.itemView.context,
//                    RequestsActivity::class.java
//                )
//            );
        }
    }

    override fun getItemCount(): Int {
        return 20;
    }
}