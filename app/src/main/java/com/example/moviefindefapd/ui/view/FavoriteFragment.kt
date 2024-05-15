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
import com.example.moviefindefapd.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    var bindingclass: FragmentFavoriteBinding? = null

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingclass = FragmentFavoriteBinding.inflate(inflater, container, false)
        return bindingclass!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.navController = findNavController()
        bindingclass!!.navigation.menu.findItem(R.id.favorite).isChecked = true
        bindingclass!!.navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    sharedViewModel.navController?.navigate(R.id.homeFragment2)
                    true
                }
                R.id.search -> {
                    sharedViewModel.navController?.navigate(R.id.searchFragment)
                    true
                }
                R.id.settings -> {
                    sharedViewModel.navController?.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }

    }

}