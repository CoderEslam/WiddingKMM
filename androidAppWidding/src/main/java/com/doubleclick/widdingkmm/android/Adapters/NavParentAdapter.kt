package com.doubleclick.widdingkmm.android.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Model.NavModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.CircleImageView


/**
 * Created By Eslam Ghazy on 7/15/2022
 */
class NavParentAdapter(var navModel: ArrayList<NavModel>) :
    RecyclerView.Adapter<NavParentAdapter.NavParentViewHolder>() {
    class NavParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: CircleImageView = itemView.findViewById(R.id.circleImageView);
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavParentViewHolder {
        return NavParentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.nav_layout_parent, parent, false)
        );
    }

    override fun onBindViewHolder(holder: NavParentViewHolder, position: Int) {
//        holder.image.setImageResource(navModel[position].image)
        holder.name.text = navModel[position].name
        holder.itemView.setOnClickListener {
//            Toast.makeText(holder.itemView.context, "" + position, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return navModel.size
    }
}