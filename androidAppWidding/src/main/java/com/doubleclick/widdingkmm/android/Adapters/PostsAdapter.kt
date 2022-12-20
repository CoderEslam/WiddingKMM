package com.doubleclick.widdings.Adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.CommentActivity
import com.doubleclick.widdingkmm.android.HashtagsActivity
import com.doubleclick.widdingkmm.android.Model.FbReactions.Reactions
import com.doubleclick.widdingkmm.android.Model.PostModelData
import com.doubleclick.widdingkmm.android.Model.Reaction
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.Views.CircleImageView
import com.doubleclick.widdingkmm.android.Views.socialtextview.SocialTextView
import com.doubleclick.widdingkmm.android.ui.Chat.ChatActivity


/**
 * Created By Eslam Ghazy on 7/14/2022
 */
class PostsAdapter(
    val activity: Activity,
    val postModelData: List<PostModelData>,
) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>(), ReactionAdapter.GetReaction {
    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postRecycler: RecyclerView = itemView.findViewById(R.id.postRecycler);
        val chatLayout: LinearLayout = itemView.findViewById(R.id.chatLayout);
        val likeButton: LinearLayout = itemView.findViewById(R.id.likeButton);
        val comment: LinearLayout = itemView.findViewById(R.id.comment);
        val share: LinearLayout = itemView.findViewById(R.id.share);
        val reactions: RecyclerView = itemView.findViewById(R.id.reactions);
        val image: CircleImageView = itemView.findViewById(R.id.image);
        val name: TextView = itemView.findViewById(R.id.name);
        val caption: SocialTextView = itemView.findViewById(R.id.caption);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.postRecycler.adapter =
            ImagesAdapter(activity, postModelData[position].postModel.getList());
        Glide.with(holder.itemView.context).load(postModelData[position].user.image)
            .into(holder.image)
        holder.name.text = postModelData[position].user.name
        holder.caption.setLinkText(postModelData[position].postModel.caption)
        holder.likeButton.setOnLongClickListener {

            holder.reactions.visibility = View.VISIBLE
            holder.reactions.adapter =
                ReactionAdapter(
                    Reactions(),
                    this@PostsAdapter,
                    holder.adapterPosition,
                    holder.reactions
                )
            true;
        }
        holder.likeButton.setOnClickListener {

        }
        holder.comment.setOnClickListener {
            val intent = Intent(holder.itemView.context, CommentActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
        holder.share.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatActivity::class.java);
            intent.putExtra("userId", postModelData[position].postModel.userId);
            intent.putExtra("postData", postModelData[position])
            holder.itemView.context.startActivity(intent)
        }
        holder.chatLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
            intent.putExtra("userId", postModelData[position].user.id);
            holder.itemView.context.startActivity(intent)
        }

        holder.caption.setOnLinkClickListener(object : SocialTextView.OnLinkClickListener {
            override fun onLinkClicked(linkType: Int, matchedText: String?) {
                if (linkType == 1) {
                    val intent = Intent(holder.itemView.context, HashtagsActivity::class.java);
                    intent.putExtra("hashtag", matchedText.toString());
                    holder.itemView.context.startActivity(intent);
                }
            }

        })
    }

    override fun getItemCount(): Int {
        return postModelData.size;
    }

    override fun getReact(reaction: Reaction, recyclerView: RecyclerView, pos: Int) {
        Log.e("reaction", reaction.toString());
        recyclerView.visibility = View.GONE
    }

    /* private fun showLayout() {
         val radius = Math.max(continer_attacht.width, continer_attacht.height).toFloat()
         val animator =
             ViewAnimationUtils.createCircularReveal(
                 continer_attacht,
                 continer_attacht.left,
                 continer_attacht.top,
                 0f,
                 radius * 2
             )
         animator.duration = 500
         continer_attacht.visibility = View.VISIBLE
         animator.start()
     }


     private fun hideLayout() {
         val radius = Math.max(continer_attacht.width, continer_attacht.height).toFloat()
         val animator =
             ViewAnimationUtils.createCircularReveal(
                 continer_attacht,
                 continer_attacht.left,
                 continer_attacht.top,
                 radius * 2,
                 0f
             )
         animator.duration = 500
         animator.addListener(object : Animator.AnimatorListener {
             override fun onAnimationStart(animation: Animator) {}
             override fun onAnimationEnd(animation: Animator) {
                 continer_attacht.visibility = View.GONE
             }

             override fun onAnimationCancel(animation: Animator) {}
             override fun onAnimationRepeat(animation: Animator) {}
         })
         animator.start()
     }*/

}