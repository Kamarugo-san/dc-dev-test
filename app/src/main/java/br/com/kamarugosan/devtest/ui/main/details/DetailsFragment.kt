package br.com.kamarugosan.devtest.ui.main.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.kamarugosan.devtest.R
import br.com.kamarugosan.devtest.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        val forecastWrapper = args.forecastWrapper
        val date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_YEAR, 1)

        binding.backFab.setOnClickListener { findNavController().popBackStack() }

        val adapter = ForecastAdapter(forecastWrapper.getForecastOfDay(date.time))
        binding.apply {
            cityTextView.text = forecastWrapper.cityName
            val format = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
            dateTextView.text = getString(R.string.details_date, format.format(date.time))

            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null

            val uri = Uri.parse("https://www.weatherbit.io/")
            val visitWeatherbitIntent = Intent(Intent.ACTION_VIEW, uri)
            weatherbitTextView.setOnClickListener { startActivity(visitWeatherbitIntent) }
        }
    }
}