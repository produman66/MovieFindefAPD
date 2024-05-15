package com.example.moviefindefapd.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviefindefapd.R
import com.example.moviefindefapd.databinding.FragmentSingInBinding

class SingInFragment : Fragment() {

    var bindingclass: FragmentSingInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingclass = FragmentSingInBinding.inflate(inflater, container, false)

        return bindingclass!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingclass!!.btnToSignUp.setOnClickListener {
            findNavController().navigate(
                R.id.action_singInFragment_to_signUpFragment
            )
        }

        bindingclass!!.btnSignIn.setOnClickListener {
            findNavController().navigate(
                R.id.homeFragment2
            )
        }
    }
}