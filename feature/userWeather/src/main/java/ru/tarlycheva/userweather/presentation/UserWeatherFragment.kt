package ru.tarlycheva.userweather.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.tarlycheva.core.userLocation.domain.repository.LocationRepository
import ru.tarlycheva.core.userLocation.domain.service.LocationService
import ru.tarlycheva.userWeather.databinding.FragmentUserWeatherBinding

@AndroidEntryPoint
class UserWeatherFragment : Fragment() {

    private var _binding: FragmentUserWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<UserWeatherViewModel>()
    private var locationRepository: LocationRepository? = null

    private var loadingIndicator: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationRepository =
            LocationRepository(applicationContext = requireActivity().applicationContext)
        locationRepository?.getLocation(
            successCallback = { address ->
                viewModel.setAddress(address)
            },
            errorCallback = {}
        )

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.loadingView.isVisible = it.first
            loadingIndicator = ""
            (0..it.second).forEach { _ ->
                loadingIndicator += "."
            }
            binding.loadingIndicator.text = loadingIndicator
        })

        viewModel.cityName.observe(viewLifecycleOwner, Observer {
            binding.cityName.text = it
        })

        viewModel.countryName.observe(viewLifecycleOwner, Observer {
            binding.countryName.text = it
        })

//
//        viewModel.weatherData.observe(viewLifecycleOwner, Observer { data ->
//            data?.current?.temp_c?.let {
//                binding.temperature.text = it.toString()
//            }
//            data?.current?.condition?.text?.let {
//                binding.weatherType.text = it
//            }
//        })

        requestPermissions()

    }

    override fun onResume() {
        super.onResume()

        initLocationService()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopLocationService()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        stopLocationService()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSIONS_CODE_REQUEST
        )
    }

    private fun initLocationService() {
        Intent(requireActivity().applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            requireActivity().startService(this)
        }
    }

    private fun stopLocationService() {
        Intent(requireActivity().applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            requireActivity().startService(this)
        }
    }

    companion object {
        private const val PERMISSIONS_CODE_REQUEST = 801
    }

//    override fun setLocationData(city: String) {
//        viewModel.setCityName(city)
//    }
//
//    override fun setLocationError() {
//
//    }

}