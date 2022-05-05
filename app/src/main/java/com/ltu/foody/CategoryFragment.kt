package com.ltu.foody

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ltu.foody.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        binding.mainCourse.setOnClickListener {
            findNavController().navigate(
                CategoryFragmentDirections.actionFirstFragmentToSecondFragment("main course")
            )
        }
        binding.salad.setOnClickListener {
            findNavController().navigate(
                CategoryFragmentDirections.actionFirstFragmentToSecondFragment("salad")
            )
        }
        binding.bread.setOnClickListener {
            findNavController().navigate(
                CategoryFragmentDirections.actionFirstFragmentToSecondFragment("bread")
            )
        }
        binding.dessert.setOnClickListener {
            findNavController().navigate(
                CategoryFragmentDirections.actionFirstFragmentToSecondFragment("dessert")
            )
        }
        binding.sauce.setOnClickListener {
            findNavController().navigate(
                CategoryFragmentDirections.actionFirstFragmentToSecondFragment("soup")
            )
        }
        binding.drink.setOnClickListener {
            findNavController().navigate(
                CategoryFragmentDirections.actionFirstFragmentToSecondFragment("drink")
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


