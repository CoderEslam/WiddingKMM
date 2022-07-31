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

class HomeFragment : Fragment() {


    private lateinit var mainRecycler: RecyclerView
    private lateinit var myImage: CircleImageView
    private lateinit var animationView: LottieAnimationView
    private lateinit var userViewModel: UserViewModel
    private lateinit var postsViewModel: PostsViewModel;
    private var advertisements: ArrayList<SlideModel> = ArrayList();
    private var navModel: ArrayList<NavModel> = ArrayList();
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        animationView = view.findViewById(R.id.animationView)
        myImage = view.findViewById(R.id.myImage);
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java];
        postsViewModel = ViewModelProvider(this)[PostsViewModel::class.java];
        userViewModel.getUserDate().observe(viewLifecycleOwner) {
            Glide.with(this).load(it.image).into(myImage);
        }
        postsViewModel.getLiveListData().observe(viewLifecycleOwner) {
            mainRecycler.adapter = PostsAdapter(requireActivity(), it);
            Log.e("POSTDATAMODEL", it.toString());
        }


        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))
        advertisements.add(SlideModel(R.drawable.download, ScaleTypes.CENTER_INSIDE))


        /*
        * for Nav Side
        * */
        navModel.add(NavModel(resources.getString(R.string.Ateliers), R.drawable.atelier, 0))
        navModel.add(
            NavModel(
                resources.getString(R.string.MakeupArtist),
                R.drawable.makup_artist,
                0
            )
        )
        navModel.add(
            NavModel(
                resources.getString(R.string.WedddingHells),
                R.drawable.weddinghell,
                0
            )
        )
        navModel.add(
            NavModel(
                resources.getString(R.string.weddingPlanner),
                R.drawable.wedding_planner,
                0
            )
        )
        //==========================================================================================

        myImage.setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileActivity::class.java));
        }

        animationView.setOnClickListener {
            startActivity(Intent(requireActivity(), ChatListActivity::class.java));
        }
    }

}