package com.ltu.foody.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.foody.model.Recipes

class MealDetailViewModelFactory(
    private val application: Application,
    private val recipes: Recipes
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
            return MealDetailViewModel(application,recipes) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}