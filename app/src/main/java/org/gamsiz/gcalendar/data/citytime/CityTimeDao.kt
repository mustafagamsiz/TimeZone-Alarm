package org.gamsiz.gcalendar.data.citytime

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityTimeDao {

    @Insert
    fun insert(cityTime: CityTime)

    @Query("SELECT * FROM city_time_table")
    fun getCityTimes(): LiveData<List<CityTime>>

    @Insert
    fun update(cityTime: CityTime)

    @Delete
    fun delete(cityTime: CityTime)

}
