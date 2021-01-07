package org.gamsiz.gcalendar.ui.citytime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.gamsiz.gcalendar.data.citytime.CityTime
import org.gamsiz.gcalendar.data.citytime.CityTimeDao

class SearchCityTimeViewModel(private val cityTimeDao: CityTimeDao, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _finishedEvent = MutableLiveData<Boolean>()

    val finishedEvent: LiveData<Boolean>
        get() = _finishedEvent

    private var _searchedEvent = MutableLiveData<Boolean>()

    val searchedEvent: LiveData<Boolean>
        get() = _searchedEvent
    
    fun onFinished() {
        _finishedEvent.value = true
    }

    fun doneCityCreated() {
        _finishedEvent.value = false
    }

    fun onSearched() {
        _searchedEvent.value = true
    }

    fun doneSearched() {
        _searchedEvent.value = false
    }

    fun onSearchCity(searcyQuery: CitySearch) {
        uiScope.launch {
            val search = search(searcyQuery.getQuery())
            searcyQuery.setQueryResult(search)
            onSearched();
        }
    }

    private suspend fun search(cityName: String): List<CityTime>? {
        return withContext(Dispatchers.IO) {
            var result: List<CityTime>? = null
            if ("I".equals(cityName)) {
                result = ArrayList<CityTime>()
                result.add(CityTime("Islamabad", "", "",7200, 0, System.currentTimeMillis()))
                result.add(CityTime("Istanbul", "", "",7200, 0, System.currentTimeMillis()))
                result.add(CityTime("Izmir", "", "",7200, 0, System.currentTimeMillis()))
            } else if ("Berlin".equals(cityName) || "Istanbul".equals(cityName)) {
                result = ArrayList<CityTime>()
                result.add(CityTime(cityName, "", "",7200, 0, System.currentTimeMillis()))
            }
            return@withContext result
        }
    }

    fun onCreateCity(cityTime: CityTime) {
        uiScope.launch {
            insert(cityTime)
            onFinished();
        }
    }

    private suspend fun insert(cityTime: CityTime) {
        withContext(Dispatchers.IO) {
            cityTimeDao.insert(cityTime)
        }
    }

}