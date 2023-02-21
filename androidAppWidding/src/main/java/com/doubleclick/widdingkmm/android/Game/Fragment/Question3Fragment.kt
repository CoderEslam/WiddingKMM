package com.doubleclick.widdingkmm.android.Game.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.`interface`.setOnGame
import com.doubleclick.widdingkmm.android.databinding.FragmentQuestion2Binding
import com.doubleclick.widdingkmm.android.databinding.FragmentQuestion3Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Question3Fragment : Fragment() {


    private lateinit var binding: FragmentQuestion3Binding


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
        binding = FragmentQuestion3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageSelect1.setOnClickListener {
            binding.selected1.visibility = View.VISIBLE
            binding.selected2.visibility = View.GONE
            array.add("pastela chicken")
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
            array.add("pastela fish")
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
                    findNavController().navigate(
                        Question3FragmentDirections.actionQuestion3FragmentToQuestion4Fragment()
                    )
                }
            }
        })
    }

}