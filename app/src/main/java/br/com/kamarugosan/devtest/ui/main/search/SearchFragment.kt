package br.com.kamarugosan.devtest.ui.main.search

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.kamarugosan.devtest.R
import br.com.kamarugosan.devtest.databinding.FragmentSearchBinding
import br.com.kamarugosan.devtest.model.Resource
import br.com.kamarugosan.devtest.ui.main.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: WeatherViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var seeDetails = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)

        binding.apply {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        seeDetails = true
                        viewModel.searchCity(query)

                        val imm: InputMethodManager =
                            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = true
            })
            searchRetryBtn.setOnClickListener {
                viewModel.retrySearch()
            }
        }

        viewModel.forecastWrapper.observe(viewLifecycleOwner) { forecastResource ->
            binding.apply {
                startHintTextView.isVisible = false
                progressIndicator.isVisible = forecastResource is Resource.Loading

                searchFailedTextView.isVisible = forecastResource is Resource.Error
                searchRetryBtn.isVisible = forecastResource is Resource.Error

                if (forecastResource is Resource.Success) {
                    val forecast = forecastResource.data!!

                    searchViewDetailsBtn.setOnClickListener {
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(forecast)
                        findNavController().navigate(action)
                    }

                    if (seeDetails) {
                        searchViewDetailsBtn.callOnClick()
                        seeDetails = false
                        return@apply
                    }

                    searchSuccessTextView.isVisible = true
                    searchSuccessTextView.text =
                        getString(R.string.city_search_success, forecast.cityName)
                    searchViewDetailsBtn.isVisible = true
                } else {
                    searchSuccessTextView.isVisible = false
                    searchViewDetailsBtn.isVisible = false
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}