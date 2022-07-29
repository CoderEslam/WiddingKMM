package com.doubleclick.widdings.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Model.Reaction
import com.doubleclick.widdingkmm.android.R

/**
 * Created By Eslam Ghazy on 7/19/2022
 */
class ReactionAdapter : RecyclerView.Adapter<ReactionAdapter.ReactionViewHolder> {


    private var reactions: ArrayList<Reaction> = ArrayList<Reaction>()
    private lateinit var getReaction: GetReaction
    private var pos = 0
    private lateinit var recyclerView: RecyclerView

    constructor(
        reactions: ArrayList<Reaction>,
        getReaction: GetReaction,
        pos: Int,
        recyclerView: RecyclerView
    ) {
        this.reactions = reactions
        this.getReaction = getReaction
        this.pos = pos
        this.recyclerView = recyclerView
    }

    class ReactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reaction_image: ImageView = itemView.findViewById(R.id.reaction_image);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReactionViewHolder {
        return ReactionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.react_dialog_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReactionViewHolder, position: Int) {
        holder.reaction_image.setImageResource(reactions[position].getReactIconId())
        holder.reaction_image.setOnClickListener {
            getReaction.getReact(
                reactions[position],
                recyclerView,
                pos
            )
        }
    }

    override fun getItemCount(): Int {
        return reactions.size;
    }

    interface GetReaction {
        fun getReact(reaction: Reaction, recyclerView: RecyclerView, pos: Int)
    }
}