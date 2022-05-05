package com.ltu.foody.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.foody.model.Recipes
import com.ltu.foody.network.DataFetchStatus
import com.ltu.foody.network.RandomMealResponse
import com.ltu.foody.network.SpoonacularApi
import kotlinx.coroutines.launch

class MealListViewModel(application: Application) :
    AndroidViewModel(application) {


    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }


    private val _recipes = MutableLiveData<List<Recipes>>()
    val recipes: LiveData<List<Recipes>>
        get() {
            return _recipes
        }


    private val _navigateToRecipesDetail = MutableLiveData<Recipes?>()
    val navigateToRecipesDetail: MutableLiveData<Recipes?>
        get() {
            return _navigateToRecipesDetail
        }


    init {
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun getRandomMeals(tag:String){
        viewModelScope.launch {
             try {
                 val randomMealResponse:RandomMealResponse = SpoonacularApi.foodListRetrofitService.getRandomMeals(tag)
                 _recipes.value = randomMealResponse.recipes
                 _dataFetchStatus.value = DataFetchStatus.DONE
             }catch (e:Exception){
                 _dataFetchStatus.value = DataFetchStatus.ERROR
                 _recipes.value = arrayListOf()
             }
        }
    }

    fun onMealListItemClicked(recipes: Recipes){
        _navigateToRecipesDetail.value = recipes
    }

    fun onMealDetailNavigated() {
        _navigateToRecipesDetail.value = null
    }
}
