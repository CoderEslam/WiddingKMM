package com.doubleclick.widdingkmm.android.Game.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.`interface`.setOnGame
import com.doubleclick.widdingkmm.android.databinding.FragmentQuestion4Binding
import com.doubleclick.widdingkmm.android.databinding.FragmentQuestion5Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Question5Fragment : Fragment() {
    private lateinit var binding: FragmentQuestion5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuestion5Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageSelect1.setOnClickListener {
            binding.selected1.visibility = View.VISIBLE
            binding.selected2.visibility = View.GONE
            array.add("yellow gold")
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1000)
                binding.linearLayoutChoose.visibility = View.GONE
                binding.maze.root.visibility = View.VISIBLE
                binding.question.setText("Vamos a conseguir lo que has elegido")
                binding.maze.circleImageSelected.setImageDrawable(binding.imageSelect1.drawable)
            }
        }

        binding.imageSelect2.setOnClickListener {
            binding.selected1.visibility = View.GONE
            binding.selected2.visibility = View.VISIBLE
            array.add("white gold")
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1000)
                binding.linearLayoutChoose.visibility = View.GONE
                binding.maze.root.visibility = View.VISIBLE
                binding.question.setText("Vamos a conseguir lo que has elegido")
                binding.maze.circleImageSelected.setImageDrawable(binding.imageSelect2.drawable)
            }
        }

        binding.maze.view.onItemGameChange(object : setOnGame {
            override fun setOnClickGame(stat: Int) {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().navigate(Question5FragmentDirections.actionQuestion5FragmentToQuestion6Fragment())
                }
            }
        })
    }
}