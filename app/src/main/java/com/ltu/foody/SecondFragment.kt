package com.ltu.foody

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.foody.adapter.MealListAdapter
import com.ltu.foody.adapter.MealListClickListener
import com.ltu.foody.databinding.FragmentSecondBinding
import com.ltu.foody.viewmodel.MealListViewModel
import com.ltu.foody.viewmodel.MealListViewModelFactory
import kotlin.reflect.jvm.internal.impl.renderer.KeywordStringsGenerated

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: MealListViewModel
    private lateinit var viewModelFactory: MealListViewModelFactory





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        viewModelFactory = MealListViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[MealListViewModel::class.java]
        val mealListAdapter = MealListAdapter(MealListClickListener { meal ->
            viewModel.onMealListItemClicked(meal)
        })
        binding.mealsListRv.adapter = mealListAdapter
        viewModel.getRandomMeals(viewModel.cat)
        viewModel.recipes.observe(viewLifecycleOwner){  mealList ->
            mealList?.let {
                mealListAdapter.submitList(mealList)
            }
        }
        viewModel.navigateToRecipesDetail.observe(viewLifecycleOwner){ meal->
            meal?.let {
                this.findNavController().navigate(
                    SecondFragmentDirections.actionSecondFragmentToThirdFragment(meal)
                )
                viewModel.onMealDetailNavigated()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}