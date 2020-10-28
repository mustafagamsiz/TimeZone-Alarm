package org.gamsiz.gcalendar.data.citytime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "city_time_table")
data class CityTime(
    @ColumnInfo
    var cityName: String,
    @ColumnInfo
    var location: String,
    @ColumnInfo
    var timeZone: String,
    @ColumnInfo
    var utcOffset: Long,
    @ColumnInfo
    var daylightOffset: Long,
    @ColumnInfo
    var lastUpdate: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L
}
