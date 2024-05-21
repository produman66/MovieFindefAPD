package com.example.moviefindefapd.ui.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.moviefindefapd.R
import com.example.moviefindefapd.data.models.UserData
import com.example.moviefindefapd.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    var bindingclass: FragmentSignUpBinding? = null

    private lateinit var firebaseRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingclass = FragmentSignUpBinding.inflate(inflater, container, false)
        return bindingclass!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseRef = FirebaseDatabase.getInstance().getReference("users")


        bindingclass?.floatingActionButton?.setOnClickListener {
            findNavController().popBackStack()
        }

        bindingclass?.btnSighUp?.setOnClickListener {
            createUser()
        }

        bindingclass?.btnToSignUp?.setOnClickListener {
            SharedPreferencesHelper.saveStringToPreferences(requireContext(), "null")
            findNavController().navigate(
                R.id.action_signUpFragment_to_homeFragment2
            )
        }
    }

    private fun createUser() {
        val name = bindingclass!!.etLoginText.text.toString()
        val password = bindingclass!!.etPasswordText.text.toString()
        val passwordRepeat = bindingclass!!.etPasswordRepeatText.text.toString()

        if (name.isEmpty()){
            bindingclass!!.etLoginText.error = "write a login"
        }
        else if  (password.isEmpty()){
            bindingclass!!.etPasswordText.error = "write a password"
        }
        else if (password != passwordRepeat){
            bindingclass!!.etPasswordRepeatText.error = "wrong repeat password"
        }
        else {
            val users = UserData(name, password)
            firebaseRef.child(name).setValue(users)
                .addOnCompleteListener{
                    Toast.makeText(context, "complete", Toast.LENGTH_LONG).show()
                    SharedPreferencesHelper.saveStringToPreferences(requireContext(), name)
                    findNavController().navigate(
                        R.id.action_signUpFragment_to_homeFragment2
                    )

                }
                .addOnFailureListener{
                    Toast.makeText(context, "error:${it.message}", Toast.LENGTH_LONG).show()
                }
        }

    }



}