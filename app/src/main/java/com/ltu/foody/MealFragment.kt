package com.ltu.foody

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.foody.adapter.MealListAdapter
import com.ltu.foody.adapter.MealListClickListener
import com.ltu.foody.databinding.FragmentMealBinding
import com.ltu.foody.viewmodel.MealListViewModel
import com.ltu.foody.viewmodel.MealListViewModelFactory
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.ltu.foody.database.RecipeDatabase
import com.ltu.foody.database.RecipeDatabaseDao
import com.ltu.foody.network.DataFetchStatus


class MealFragment : Fragment() {

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MealListViewModel
    private lateinit var viewModelFactory: MealListViewModelFactory
    private lateinit var recipeDatabaseDao: RecipeDatabaseDao
    private val args: MealFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentMealBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        recipeDatabaseDao = RecipeDatabase.getDatabase(application).recipeDatabaseDao()
        viewModelFactory = MealListViewModelFactory(recipeDatabaseDao,application)
        viewModel = ViewModelProvider(this, viewModelFactory)[MealListViewModel::class.java]
        val mealListAdapter = MealListAdapter(MealListClickListener { meal ->
            viewModel.onMealListItemClicked(meal)
        })
        binding.mealsListRv.adapter = mealListAdapter
        viewModel.getRandomMeals(args.category)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = (args.category).uppercase()

        viewModel.recipes.observe(viewLifecycleOwner){  mealList ->
                mealList?.let {
                    mealListAdapter.submitList(mealList)
                }
            }
            viewModel.navigateToRecipesDetail.observe(viewLifecycleOwner){ meal->
                meal?.let {
                    this.findNavController().navigate(
                        MealFragmentDirections.actionSecondFragmentToThirdFragment(meal)
                    )
                    viewModel.onMealDetailNavigated()
                }
            }

        binding.swipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            // This method gets called when user pull for refresh,
            // You can make your API call here,
            viewModel.getRandomMeals(args.category)

            val handler = Handler()
            handler.postDelayed(Runnable {
                if (binding.swipeRefreshLayout.isRefreshing) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }, 1000)
        })

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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        /**
         *  Handle action bar item clicks here. The action bar will
         *  automatically handle clicks on the Home/Up button, so long
         *  as you specify a parent activity in AndroidManifest.xml.
         */

        when (item.itemId) {
            R.id.action_saved_recipes -> {
                viewModel.getSavedRecipes()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


