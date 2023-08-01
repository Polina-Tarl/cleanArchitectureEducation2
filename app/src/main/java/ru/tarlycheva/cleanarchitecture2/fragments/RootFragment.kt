package ru.tarlycheva.cleanarchitecture2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.tarlycheva.cleanarchitecture2.databinding.FragmentRootBinding
import ru.tarlycheva.navigation.actions.ToWelcomeFragment
import ru.tarlycheva.navigation.extension.navigateGlobal

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RootFragment : Fragment() {

    private var _binding: FragmentRootBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().navigateGlobal(
            ToWelcomeFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}