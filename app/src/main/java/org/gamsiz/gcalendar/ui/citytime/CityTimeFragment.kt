package org.gamsiz.gcalendar.ui.citytime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import org.gamsiz.gcalendar.R
import org.gamsiz.gcalendar.data.GCalendarDatabase
import org.gamsiz.gcalendar.databinding.FragmentCityTimeBinding

class CityTimeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentCityTimeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_time, container, false)

        val application = requireNotNull(this.activity).application

        val cityTimeDao = GCalendarDatabase.getInstance(requireContext()).cityTimeDao()

        val viewModelFactory = CityTimeViewModelFactory(cityTimeDao, application)

        val cityTimeViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(CityTimeViewModel::class.java)

        binding.cityTimeViewModel = cityTimeViewModel

        binding.lifecycleOwner = this

        // Creates a vertical Layout Manager
        binding.cityTimeList.layoutManager = LinearLayoutManager(application)

        val cityTimeAdapter = CityTimeAdapter(
            CityTimeAdapter.CityTimeListener(
                openListener = { cityTime ->
                    /*
                    val bundle = Bundle()
                    bundle.putString("peer", cityTime.cityName)
                    Navigation.findNavController(binding.root).navigate(R.id.action_conversationFragment_to_chatFragment, bundle)
                     */
                },
                deleteListener = { cityTime ->
                    cityTimeViewModel.onDelete(cityTime)
                }
            )
        )

        binding.cityTimeList.adapter = cityTimeAdapter

        cityTimeViewModel.cityTimes.observe(viewLifecycleOwner, Observer {
            it?.let {
                cityTimeAdapter.addHeaderAndSubmitList(it)
            }
        })

        cityTimeViewModel.newCityTimeEvent.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) { // Observed state is true.
                    cityTimeViewModel.doneCreatingCityTime()
                    Navigation.findNavController(binding.root).navigate(R.id.action_navigation_city_time_to_navigation_search_city_time)
                }
            }
        })

        return binding.root
    }
}