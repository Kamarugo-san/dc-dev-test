package br.com.kamarugosan.devtest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ForecastWrapper(
    @SerializedName("city_name")
    val cityName: String,
    val data: List<Forecast>
) : Parcelable {
    fun getForecastOfDay(desiredDay: Date): List<Forecast> {
        val forecastList = mutableListOf<Forecast>()
        val desiredDateCalendar = Calendar.getInstance()
        desiredDateCalendar.time = desiredDay
        data.forEach {
            val currentDayCalendar = Calendar.getInstance()
            currentDayCalendar.time = it.localDate

            if (currentDayCalendar[Calendar.DAY_OF_YEAR] == desiredDateCalendar[Calendar.DAY_OF_YEAR] &&
                currentDayCalendar[Calendar.YEAR] == desiredDateCalendar[Calendar.YEAR]) {
                forecastList.add(it)
            }
        }

        return forecastList
    }

    @Parcelize
    data class Forecast(
        @SerializedName("ts")
        private val _localDateTime: Long,
        val temp: Float,
        @SerializedName("app_temp")
        val appTemp: Float,
        val pop: Float,
        val weather: Weather
    ) : Parcelable {
        val localDate: Date get() = Date(_localDateTime * 1000)
        @Parcelize
        data class Weather(
            val icon: String,
            val description: String
        ) : Parcelable
    }
}