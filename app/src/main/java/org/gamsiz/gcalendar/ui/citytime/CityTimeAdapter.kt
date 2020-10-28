package org.gamsiz.gcalendar.ui.citytime

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.gamsiz.gcalendar.data.citytime.CityTime

class CityTimeAdapter(private val cityTimeListener: CityTimeListener)
    : ListAdapter<CityTime, CityTimeViewHolder>(CityTimeDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<CityTime>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityTimeViewHolder {
        return CityTimeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CityTimeViewHolder, position: Int) {
        holder.bind(getItem(position), cityTimeListener)
    }

    class CityTimeDiffCallback : DiffUtil.ItemCallback<CityTime>() {
        override fun areItemsTheSame(oldItem: CityTime, newItem: CityTime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CityTime, newItem: CityTime): Boolean {
            return oldItem.cityName == newItem.cityName
        }
    }

    class CityTimeListener(val openListener: (cityTime: CityTime) -> Unit = {},
                               val deleteListener: (cityTime: CityTime) -> Unit = {}) {
        fun onOpen(cityTime: CityTime) = openListener(cityTime)
        fun onDelete(cityTime: CityTime) = deleteListener(cityTime)
    }

}