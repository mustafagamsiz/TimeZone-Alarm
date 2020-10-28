package org.gamsiz.gcalendar.ui.citytime

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.gamsiz.gcalendar.data.citytime.CityTimeDao

class SearchCityTimeViewModelFactory(private val cityTimeDao: CityTimeDao, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityTimeViewModel::class.java)) {
            return SearchCityTimeViewModel(cityTimeDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

