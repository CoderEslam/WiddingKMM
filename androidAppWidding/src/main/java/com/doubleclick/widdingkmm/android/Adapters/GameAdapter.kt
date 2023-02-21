package com.doubleclick.widdingkmm.android.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Game.Fragment.GameActivity
import com.doubleclick.widdingkmm.android.R

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameViewHoler>() {

    class GameViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHoler {
        return GameViewHoler(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GameViewHoler, position: Int) {
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(
                Intent(
                    holder.itemView.context,
                    GameActivity::class.java
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return 10;
    }
}