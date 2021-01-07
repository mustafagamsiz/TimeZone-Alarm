package org.gamsiz.gcalendar.ui.citytime

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import org.gamsiz.gcalendar.R
import org.gamsiz.gcalendar.data.GCalendarDatabase
import org.gamsiz.gcalendar.databinding.FragmentSearchCityTimeBinding

class SearchCityTimeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentSearchCityTimeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_city_time, container, false)

        val application = requireNotNull(this.activity).application

        val cityTimeDao = GCalendarDatabase.getInstance(requireContext()).cityTimeDao()

        val viewModelFactory = SearchCityTimeViewModelFactory(cityTimeDao, application)

        val searchCityTimeViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(SearchCityTimeViewModel::class.java)

        binding.searchQuery = CitySearch()
        binding.searchCityTimeViewModel = searchCityTimeViewModel

        binding.lifecycleOwner = this

        // Creates a vertical Layout Manager
        binding.citySearchResults.layoutManager = LinearLayoutManager(application)

        val cityTimeAdapter = CityTimeAdapter(
            CityTimeAdapter.CityTimeListener(
                openListener = { cityTime ->
                    searchCityTimeViewModel.onCreateCity(cityTime)
                }
            )
        )

        binding.citySearchResults.adapter = cityTimeAdapter

        searchCityTimeViewModel.searchedEvent.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    cityTimeAdapter.addHeaderAndSubmitList(binding.searchQuery!!.getQueryResult())
                    searchCityTimeViewModel.doneSearched()
                }
            }
        })

        searchCityTimeViewModel.finishedEvent.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    searchCityTimeViewModel.doneCityCreated()
                    Navigation.findNavController(binding.root).popBackStack()
                }
            }
        })

        return binding.root
    }
}