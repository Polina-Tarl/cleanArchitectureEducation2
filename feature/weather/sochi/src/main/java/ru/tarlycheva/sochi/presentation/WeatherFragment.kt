package ru.tarlycheva.sochi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.tarlycheva.navigation.actions.ToUserWeatherFragment
import ru.tarlycheva.navigation.extension.navigateGlobal
import ru.tarlycheva.weather.sochi.databinding.FragmentWeatherBinding

@AndroidEntryPoint
class WeatherFragment() : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<WeatherViewModel>()

    private var loadingIndicator: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.loadingView.isVisible = it.first
            binding.buttonUserWeather.isVisible = it.first.not()
            loadingIndicator = ""
            (0..it.second).forEach { _ ->
                loadingIndicator += "."
            }
            binding.loadingIndicator.text = loadingIndicator
        })

        viewModel.weatherData.observe(viewLifecycleOwner, Observer { data ->
            data?.current?.temp_c?.let {
                binding.temperature.text = it.toString()
            }
            data?.current?.condition?.text?.let {
                binding.weatherType.text = it
            }
        })

        binding.buttonUserWeather.setOnClickListener {
            findNavController().navigateGlobal(
                ToUserWeatherFragment()
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}