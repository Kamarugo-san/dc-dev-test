package br.com.kamarugosan.devtest.api

import br.com.kamarugosan.devtest.BuildConfig
import br.com.kamarugosan.devtest.model.ForecastWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherbitApi {
    companion object {
        const val BASE_URL = "https://api.weatherbit.io/v2.0/"
        const val API_KEY = BuildConfig.WEATHERBIT_API_KEY

        const val DEFAULT_FORECAST_HOURS = 48
        const val DEFAULT_FORECAST_LANGUAGE = "pt"
    }

    @GET("forecast/hourly")
    suspend fun getForecast(
        @Query("city") city: String,
        @Query("hours") hours: Int = DEFAULT_FORECAST_HOURS,
        @Query("lang") language: String = DEFAULT_FORECAST_LANGUAGE,
        @Query("key") key: String = API_KEY
    ): ForecastWrapper
}