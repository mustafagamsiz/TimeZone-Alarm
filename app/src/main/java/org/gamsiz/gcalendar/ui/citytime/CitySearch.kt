package org.gamsiz.gcalendar.ui.citytime

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.gamsiz.gcalendar.BR
import org.gamsiz.gcalendar.data.citytime.CityTime

class CitySearch () : BaseObservable() {

    private var query: String = ""

    private var hasResult: Boolean = false

    private var queryResult: List<CityTime>? = null

    @Bindable
    fun getQuery(): String {
        return query
    }

    fun setQuery(value: String) {
        // Avoids infinite loops.
        if (query != value) {
            query = value

            // Notify observers of a new value.
            notifyPropertyChanged(BR._all)
        }
    }

    @Bindable
    fun getHasResult(): Boolean {
        return queryResult != null
    }

    fun setHasResult(value: Boolean) {
        // Avoids infinite loops.
        if (hasResult != value) {
            hasResult = value

            // Notify observers of a new value.
            notifyPropertyChanged(BR._all)
        }
    }

    fun getQueryResult(): List<CityTime>? {
        return queryResult
    }

    fun setQueryResult(value: List<CityTime>?) {
        // Avoids infinite loops.
        if (queryResult != value) {
            queryResult = value

            setHasResult(queryResult != null)

            // Notify observers of a new value.
            notifyPropertyChanged(BR._all)
        }
    }
}