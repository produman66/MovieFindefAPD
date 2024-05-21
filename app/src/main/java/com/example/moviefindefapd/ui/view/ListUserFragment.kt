package com.example.moviefindefapd.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefindefapd.R
import com.example.moviefindefapd.data.models.Film
import com.example.moviefindefapd.data.models.UserData
import com.example.moviefindefapd.databinding.FragmentListUserBinding
import com.example.moviefindefapd.ui.adapters.FilmsAdapter
import com.example.moviefindefapd.ui.adapters.OnClickListener
import com.example.moviefindefapd.ui.adapters.OnClickListenerUser
import com.example.moviefindefapd.ui.adapters.UsersAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListUserFragment : Fragment() {
    var bindingclass: FragmentListUserBinding? = null

    private lateinit var usersList: ArrayList<UserData>
    private lateinit var firebaseRef: DatabaseReference

    private val usersAdapter by lazy{
        UsersAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingclass = FragmentListUserBinding.inflate(inflater, container, false)
        return bindingclass!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseRef = FirebaseDatabase.getInstance().getReference("users")
        usersList = arrayListOf()

        fetchData()

        bindingclass!!.recyclerUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
        bindingclass!!.recyclerUser.addItemDecoration(SpaceItemDecoration(8))

        usersAdapter!!.setOnClickListener(object : OnClickListenerUser {


            override fun onClick(position: Int, model: UserData) {
                val id = model.name.toString()
                firebaseRef.child(id).removeValue()
            }
        })
    }

    private fun fetchData() {
        firebaseRef.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children) {
                        val user = userSnap.getValue(UserData::class.java)
                        usersList.add(user!!)
                    }
                }
                usersAdapter.setUserList(usersList)
                bindingclass!!.recyclerUser.adapter = usersAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }


}