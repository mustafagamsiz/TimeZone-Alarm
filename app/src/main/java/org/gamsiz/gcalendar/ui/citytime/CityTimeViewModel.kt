package org.gamsiz.gcalendar.ui.citytime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.gamsiz.gcalendar.R
import org.gamsiz.gcalendar.data.citytime.CityTime
import org.gamsiz.gcalendar.data.citytime.CityTimeDao

class CityTimeViewModel(private val cityTimeDao: CityTimeDao, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val cityTimes = cityTimeDao.getCityTimes()

    private var _newCityTimeEvent = MutableLiveData<Boolean>()

    val newCityTimeEvent: LiveData<Boolean>
        get() = _newCityTimeEvent

    fun onNewCity() {
        _newCityTimeEvent.value = true
    }

    fun doneCreatingCityTime() {
        _newCityTimeEvent.value = false
    }

    fun onDelete(cityTime: CityTime) {
        uiScope.launch {
            delete(cityTime)
        }
    }

    private suspend fun delete(cityTime: CityTime) {
        withContext(Dispatchers.IO) {
            cityTimeDao.delete(cityTime)
        }
    }
}