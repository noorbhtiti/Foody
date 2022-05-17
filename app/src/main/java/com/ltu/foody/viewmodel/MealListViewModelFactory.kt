package com.ltu.foody.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.foody.database.RecipeDatabaseDao

class MealListViewModelFactory(
    private val recipeDatabaseDao: RecipeDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealListViewModel::class.java)) {
            return MealListViewModel(recipeDatabaseDao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}