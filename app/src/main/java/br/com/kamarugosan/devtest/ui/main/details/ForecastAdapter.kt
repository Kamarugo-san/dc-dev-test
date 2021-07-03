package br.com.kamarugosan.devtest.ui.main.details

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.kamarugosan.devtest.R
import br.com.kamarugosan.devtest.databinding.ItemForecastBinding
import br.com.kamarugosan.devtest.model.ForecastWrapper
import com.bumptech.glide.Glide
import java.util.*

class ForecastAdapter(private val list: List<ForecastWrapper.Forecast>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class ForecastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: ForecastWrapper.Forecast) {
            binding.apply {
                val currentDayCalendar = Calendar.getInstance()
                currentDayCalendar.time = forecast.localDate
                val hourOfDay = currentDayCalendar[Calendar.HOUR_OF_DAY]

                if (hourOfDay < 7 || hourOfDay > 18) {
                    timeBackground.background = ContextCompat.getDrawable(itemView.context, R.drawable.item_forecast_gradient_night)
                } else {
                    timeBackground.background = ContextCompat.getDrawable(itemView.context, R.drawable.item_forecast_gradient_day)
                }

                hourTextView.text = itemView.context.getString(R.string.details_hour, hourOfDay)
                temperatureTextView.text = itemView.context.getString(R.string.details_temperature, forecast.temp)
                apparentTemperatureTextView.text = itemView.context.getString(R.string.details_apparent_temperature, forecast.appTemp)
                rainPercentageTextView.text = itemView.context.getString(R.string.details_rain_percentage, forecast.pop)

                Glide.with(itemView.context)
                    .load(Uri.parse("file:///android_asset/${forecast.weather.icon}.png"))
                    .into(weatherIcon)
            }
        }
    }
}