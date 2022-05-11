package com.ltu.foody.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.foody.model.Ingredients
import com.ltu.foody.model.Instructions
import com.ltu.foody.model.InstructionsSteps
import com.ltu.foody.model.Recipes
import com.ltu.foody.network.DataFetchStatus
import com.ltu.foody.network.MealDetailsResponse
import com.ltu.foody.network.RandomMealResponse
import com.ltu.foody.network.SpoonacularApi
import com.squareup.moshi.Json
import kotlinx.coroutines.launch


class MealDetailViewModel(
    application: Application,
    recipes: Recipes) :
    AndroidViewModel(application) {


    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }




    private val _ingredients = MutableLiveData<List<Ingredients>>()
    val ingredients: LiveData<List<Ingredients>>
        get() {
            return _ingredients
        }

    private val _instructions = MutableLiveData<List<Instructions>>()
    val instructions: LiveData<List<Instructions>>
        get() {
            return _instructions
        }

    private val _instructionsSteps = MutableLiveData<List<InstructionsSteps>>()
    val instructionsSteps: LiveData<List<InstructionsSteps>>
        get() {
            return _instructionsSteps
        }


    private val _mealDetails = MutableLiveData<MealDetailsResponse?>()
    val mealDetails: LiveData<MealDetailsResponse?>
        get() {
            return _mealDetails
        }


    init {
        getMealDetails(recipes.id.toString())
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun getMealDetails(mealID:String){
        viewModelScope.launch {
            try {
                val mealDetailsResponse: MealDetailsResponse = SpoonacularApi.foodListRetrofitService.getMealDetails(mealID)
                _mealDetails.value = mealDetailsResponse
                _ingredients.value = mealDetailsResponse.extendedIngredients
                _instructions.value = mealDetailsResponse.analyzedInstructions
                _instructionsSteps.value = mealDetailsResponse.analyzedInstructions[0].steps
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _mealDetails.value = null
                _ingredients.value = listOf()
                _instructions.value = listOf()
            }
        }
    }
}
