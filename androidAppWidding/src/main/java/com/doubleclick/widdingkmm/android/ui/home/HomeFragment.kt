package com.doubleclick.widdingkmm.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.widdingkmm.android.Adapters.PostsAdapter
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.ViewModel.PostsViewModel

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