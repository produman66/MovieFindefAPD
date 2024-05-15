package com.example.moviefindefapd.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviefindefapd.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    var bindingclass: FragmentSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingclass = FragmentSignUpBinding.inflate(inflater, container, false)

        return bindingclass!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingclass!!.floatingActionButton.setOnClickListener{
            findNavController().popBackStack()
        }


    }

}