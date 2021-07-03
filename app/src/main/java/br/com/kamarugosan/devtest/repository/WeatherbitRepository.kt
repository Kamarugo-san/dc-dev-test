package br.com.kamarugosan.devtest.repository

import br.com.kamarugosan.devtest.api.WeatherbitApi
import br.com.kamarugosan.devtest.model.ForecastWrapper
import br.com.kamarugosan.devtest.model.Resource
import java.lang.Exception
import javax.inject.Inject

class WeatherbitRepository @Inject constructor(private val weatherbitApi: WeatherbitApi) {
    suspend fun getForecast(city: String): Resource<ForecastWrapper> {
        return try {
            Resource.Success(weatherbitApi.getForecast(city))
        } catch (exception: Exception) {
            Resource.Error(exception.message ?: "failed")
        }
    }
}