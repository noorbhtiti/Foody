package com.ltu.foody

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ltu.foody.databinding.FragmentDetailsBinding
import com.ltu.foody.model.Recipes
import com.ltu.foody.network.DataFetchStatus
import com.ltu.foody.viewmodel.MealDetailViewModel
import com.ltu.foody.viewmodel.MealDetailViewModelFactory

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MealDetailViewModel
    private lateinit var viewModelFactory: MealDetailViewModelFactory
    private lateinit var recipes: Recipes

    private var vegan:Boolean = false
    private var glutenfree:Boolean = false
    private var lactosefree:Boolean = false



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        val application = requireNotNull(this.activity).application
        recipes = DetailsFragmentArgs.fromBundle(requireArguments()).recipe
        viewModelFactory = MealDetailViewModelFactory(application,recipes)
        viewModel = ViewModelProvider(this, viewModelFactory)[MealDetailViewModel::class.java]
        binding.recipes = recipes

        viewModel.mealDetails.observe(viewLifecycleOwner){ mealDetailsResponse ->
            mealDetailsResponse?.let {
                binding.mealDetails = it
                if (it.vegan){
                    binding.vegan.visibility = View.VISIBLE
                }else{
                    binding.vegan.visibility = View.GONE
                }
                if (it.dairyFree){
                    binding.lactosefree.visibility = View.VISIBLE
                }else{
                    binding.lactosefree.visibility = View.GONE
                }
                if (it.glutenFree){
                    binding.glutenfree.visibility = View.VISIBLE
                }else{
                    binding.glutenfree.visibility = View.GONE
                }

                vegan = it.vegan
                glutenfree = it.glutenFree
                lactosefree = it.dairyFree
            }
        }
        viewModel.ingredients.observe(viewLifecycleOwner){ ingredientsList ->
            val lastIngredientsList = mutableListOf<String>()
            ingredientsList?.forEach { ingredients ->
                ingredients.original?.let { lastIngredientsList.add(it) }
            }
            binding.Ingredients.text = "Ingredients: \n" + lastIngredientsList.joinToString("\n ")
        }

        viewModel.dataFetchStatus.observe(viewLifecycleOwner){status ->
            status?.let {
                when(status){
                    DataFetchStatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImage.visibility = View.GONE
                    }
                }
            }
        }


        return binding.root
    }
}