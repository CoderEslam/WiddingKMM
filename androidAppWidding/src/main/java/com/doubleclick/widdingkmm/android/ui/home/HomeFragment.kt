package com.doubleclick.widdingkmm.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.doubleclick.widdingkmm.android.Model.NavModel
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewModel.PostsViewModel
import com.doubleclick.widdingkmm.android.ViewModel.UserViewModel
import com.doubleclick.widdingkmm.android.Views.CircleImageView
import com.doubleclick.widdingkmm.android.Views.ImageSlider.constants.ScaleTypes
import com.doubleclick.widdingkmm.android.Views.ImageSlider.models.SlideModel
import com.doubleclick.widdingkmm.android.databinding.FragmentHomeBinding
import com.doubleclick.widdingkmm.android.ui.Chat.ChatListActivity
import com.doubleclick.widdingkmm.android.ui.Profile.ProfileActivity
import com.doubleclick.widdings.Adapters.NavParentApadter
import com.doubleclick.widdings.Adapters.PostsAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeFragment : Fragment() {


    private lateinit var mainRecycler: RecyclerView
    private lateinit var postsViewModel: PostsViewModel;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRecycler = view.findViewById(R.id.mainRecycler);

        postsViewModel = ViewModelProvider(this)[PostsViewModel::class.java];

        postsViewModel.getLiveListData().observe(viewLifecycleOwner) {
            mainRecycler.adapter = PostsAdapter(requireActivity(), it);
        }



    }

}