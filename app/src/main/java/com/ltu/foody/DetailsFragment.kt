package com.ltu.foody

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ltu.foody.adapter.IngredientsListAdapter
import com.ltu.foody.adapter.InstructionsListAdapter
import com.ltu.foody.database.RecipeDatabase
import com.ltu.foody.database.RecipeDatabaseDao
import com.ltu.foody.databinding.FragmentDetailsBinding
import com.ltu.foody.model.InstructionsSteps
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
    private lateinit var recipeDatabaseDao: RecipeDatabaseDao
    private val args: DetailsFragmentArgs by navArgs()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = (args.recipe.title)

        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        val application = requireNotNull(this.activity).application
        recipes = DetailsFragmentArgs.fromBundle(requireArguments()).recipe
        recipeDatabaseDao = RecipeDatabase.getDatabase(application).recipeDatabaseDao()
        viewModelFactory = MealDetailViewModelFactory(recipeDatabaseDao,application,recipes)
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
            }
        }

        val ingredientsListAdapter = IngredientsListAdapter()
        binding.IngredientsListRv.adapter = ingredientsListAdapter

        viewModel.ingredients.observe(viewLifecycleOwner){ ingredientsList ->
            ingredientsList?.let {
                ingredientsListAdapter.submitList(ingredientsList)
            }
        }

        val instructionsListAdapter = InstructionsListAdapter()
        binding.InstructionsListRv.adapter = instructionsListAdapter

        viewModel.instructionsSteps.observe(viewLifecycleOwner){ listInstructionSteps ->
            listInstructionSteps?.let {
                instructionsListAdapter.submitList(listInstructionSteps)
            }
        }


        viewModel.dataFetchStatus.observe(viewLifecycleOwner){status ->
            status?.let {
                when(status){
                    DataFetchStatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                        binding.bigImage.visibility = View.GONE
                        binding.Ingredients.visibility = View.GONE
                        binding.Instructions.visibility = View.GONE
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                        binding.bigImage.visibility = View.GONE
                        binding.Ingredients.visibility = View.GONE
                        binding.Instructions.visibility = View.GONE
                        binding.saveToDBButtonView.visibility = View.GONE
                        binding.removeFromDBButtonView.visibility = View.GONE
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImage.visibility = View.GONE
                        binding.bigImage.visibility = View.VISIBLE
                        binding.Ingredients.visibility = View.VISIBLE
                        binding.Instructions.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner){   isFavorite ->
            isFavorite?.let {
                when(isFavorite){
                    true ->{
                        binding.saveToDBButtonView.visibility = View.GONE
                        binding.removeFromDBButtonView.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.saveToDBButtonView.visibility = View.VISIBLE
                        binding.removeFromDBButtonView.visibility = View.GONE
                    }
                }
            }

        }

        binding.saveToDBButtonView.setOnClickListener {
            viewModel.onAddToDBButtonClicked(recipes)
        }
        binding.removeFromDBButtonView.setOnClickListener {
            viewModel.onRemoveFromDBButtonClicked(recipes)
        }
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}