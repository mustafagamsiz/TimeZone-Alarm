package org.gamsiz.gcalendar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.gamsiz.gcalendar.data.citytime.CityTime
import org.gamsiz.gcalendar.data.citytime.CityTimeDao

@Database(entities = [CityTime::class],
    version = 1)
abstract class GCalendarDatabase : RoomDatabase() {

    abstract fun cityTimeDao(): CityTimeDao

    companion object {
        @Volatile
        private var INSTANCE: GCalendarDatabase? = null

        fun getInstance(context: Context) : GCalendarDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, GCalendarDatabase::class.java, "gcalendar_db")
                        .build()
                }
                return INSTANCE as GCalendarDatabase
            }
        }
    }

}