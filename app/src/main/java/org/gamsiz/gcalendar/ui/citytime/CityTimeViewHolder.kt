package org.gamsiz.gcalendar.ui.citytime

import android.content.Context
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.RecyclerView
import org.gamsiz.gcalendar.data.citytime.CityTime
import org.gamsiz.gcalendar.databinding.ListItemCityTimeBinding

class CityTimeViewHolder(private val binding: ListItemCityTimeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CityTime, cityTimeListener: CityTimeAdapter.CityTimeListener) {
        binding.cityTime = item
        binding.cityTimeListener = cityTimeListener
        binding.executePendingBindings()
        binding.cityTimeLabel.setOnTouchListener(OnSwipeTouchListener(binding.root.context))
    }

    companion object {
        fun create(parent: ViewGroup): CityTimeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemCityTimeBinding.inflate(layoutInflater, parent, false)

            return CityTimeViewHolder(binding)
        }
    }

    private inner class OnSwipeTouchListener(ctx: Context?) : OnTouchListener {
        private val gestureDetector: GestureDetector
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        private inner class GestureListener : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

        }

        init {
            gestureDetector = GestureDetector(ctx, GestureListener())
        }
    }
}