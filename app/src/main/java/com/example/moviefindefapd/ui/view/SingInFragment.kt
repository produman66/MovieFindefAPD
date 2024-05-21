package com.example.moviefindefapd.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.moviefindefapd.R
import com.example.moviefindefapd.data.models.UserData
import com.example.moviefindefapd.databinding.FragmentSingInBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
            signIn()
        }
    }

    private fun signIn() {
        val name = bindingclass!!.etLoginText.text.toString()
        val password = bindingclass!!.etPasswordText.text.toString()

        if (name.isEmpty()){
            bindingclass!!.etLoginText.error = "write a login"
        }
        else if  (password.isEmpty()){
            bindingclass!!.etPasswordText.error = "write a password"
        }
        else {
            if (name == "admin" && password == "123"){
                findNavController().navigate(
                    R.id.action_singInFragment_to_listUserFragment
                )
            }
            if (name == "moder" && password == "123"){
                
            }
            else {
                val firebaseRef = FirebaseDatabase.getInstance().getReference("users")
                firebaseRef.child(name).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val user = dataSnapshot.getValue(UserData::class.java)
                            if (user != null) {
                                if (user.password == password){
                                    findNavController().navigate(
                                        R.id.homeFragment2
                                    )
                                }
                                else {
                                    Toast.makeText(context, "Пароль неверный", Toast.LENGTH_LONG).show()
                                }
                            }
                            else {
                                Toast.makeText(context, "User data is null", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                    }
                })
            }

        }
    }
}