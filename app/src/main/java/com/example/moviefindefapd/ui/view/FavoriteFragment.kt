package com.example.moviefindefapd.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviefindefapd.R
import com.example.moviefindefapd.data.models.DescriprionMovies
import com.example.moviefindefapd.data.models.UserData
import com.example.moviefindefapd.ui.view_models.SharedViewModel
import com.example.moviefindefapd.databinding.FragmentFavoriteBinding
import com.example.moviefindefapd.ui.adapters.MovieAdapter
import com.example.moviefindefapd.ui.adapters.OnClickListenerFilm
import com.example.moviefindefapd.ui.view_models.MovieViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FavoriteFragment : Fragment() {

    private var bindingclass: FragmentFavoriteBinding? = null
    private val viewModel by lazy { ViewModelProvider(this)[MovieViewModel::class.java] }
    private val movieAdapter by lazy { MovieAdapter() }
    private lateinit var sharedViewModel: SharedViewModel
    private val favoriteList: MutableList<String> = mutableListOf()
    private val filmList: MutableList<DescriprionMovies> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val userId = SharedPreferencesHelper.getStringFromPreferences(requireContext())
        if (userId != null) {
            getFavoriteFilm(userId)
        } else {
            Toast.makeText(requireContext(), "User ID not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingclass = FragmentFavoriteBinding.inflate(inflater, container, false)
        return bindingclass!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()


        sharedViewModel.navController = findNavController()
        bindingclass!!.navigation.menu.findItem(R.id.favorite).isChecked = true
        bindingclass!!.navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search -> {
                    sharedViewModel.navController?.navigate(R.id.searchFragment)
                    true
                }
                R.id.home -> {
                    sharedViewModel.navController?.navigate(R.id.homeFragment2)
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

    private fun setupUI() {
        prepareRecyclerView()
        movieAdapter.setOnClickListener(object : OnClickListenerFilm {
            override fun onClick(position: Int, model: DescriprionMovies) {
                val id = model.kinopoiskId.toString()
                Log.d("filmId", model.toString())
                val bundle = bundleOf("filmId" to id)
                findNavController().navigate(R.id.action_favoriteFragment_to_movieFragment, bundle)
            }
        })
    }

    private fun prepareRecyclerView() {
        bindingclass!!.recyclerFavorite.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = movieAdapter
        }
        bindingclass!!.recyclerFavorite.addItemDecoration(SpaceItemDecoration(8))
    }

    private fun getFavoriteFilm(userId: String) {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("users")
        firebaseRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(UserData::class.java)
                    if (user != null) {
                        favoriteList.addAll(user.favoriteFilm.toList())
                        loadFavoriteFilms()
                    } else {
                        Toast.makeText(context, "User data is null", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    Toast.makeText(context, "Зарегистрируйтесь, чтобы добавить избранное", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadFavoriteFilms() {
        if (favoriteList.isEmpty()) {
            updateAdapter()
            return
        }

        favoriteList.forEach { filmId ->
            viewModel.loadData(filmId)
        }

        viewModel.movieData.observe(viewLifecycleOwner, Observer { movie ->
            filmList.add(movie)
            if (filmList.size == favoriteList.size) {
                updateAdapter()
            }
        })
    }

    private fun updateAdapter() {
        movieAdapter.setMovieList(filmList.toList())
        bindingclass!!.recyclerFavorite.adapter = movieAdapter
    }
}
