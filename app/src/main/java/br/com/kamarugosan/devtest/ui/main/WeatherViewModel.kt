package br.com.kamarugosan.devtest.ui.main

import androidx.lifecycle.*
import br.com.kamarugosan.devtest.model.ForecastWrapper
import br.com.kamarugosan.devtest.model.Resource
import br.com.kamarugosan.devtest.repository.WeatherbitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherbitRepository
) : ViewModel() {
    private val currentSearch = MutableLiveData<String>()

    val forecastWrapper: LiveData<Resource<ForecastWrapper>> = currentSearch.switchMap { city ->
        val liveData: LiveData<Resource<ForecastWrapper>> = liveData {
            emit(Resource.Loading())

            emit(repository.getForecast(city))
        }
        liveData
    }

    fun searchCity(city: String) {
        currentSearch.value = city
    }

    fun retrySearch() {
        if (currentSearch.value != null) {
            currentSearch.value = currentSearch.value
        }
    }
}