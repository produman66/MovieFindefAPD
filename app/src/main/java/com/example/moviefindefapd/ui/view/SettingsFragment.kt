package com.example.moviefindefapd.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviefindefapd.R
import com.example.moviefindefapd.ui.view_models.SharedViewModel
import com.example.moviefindefapd.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    var bindingclass: FragmentSettingsBinding? = null
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingclass = FragmentSettingsBinding.inflate(inflater, container, false)
        return bindingclass!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.navController = findNavController()
        bindingclass!!.navigation.menu.findItem(R.id.settings).isChecked = true
        bindingclass!!.navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    sharedViewModel.navController?.navigate(R.id.homeFragment2)
                    true
                }
                R.id.favorite -> {
                    sharedViewModel.navController?.navigate(R.id.favoriteFragment)
                    true
                }
                R.id.search -> {
                    sharedViewModel.navController?.navigate(R.id.searchFragment)
                    true
                }
                else -> false
            }
        }
    }

}