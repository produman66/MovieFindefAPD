package com.example.moviefindefapd.ui.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviefindefapd.R
import com.example.moviefindefapd.data.models.Film
import com.example.moviefindefapd.ui.view_models.SharedViewModel
import com.example.moviefindefapd.databinding.FragmentSearchBinding
import com.example.moviefindefapd.ui.adapters.FilmsAdapter
import com.example.moviefindefapd.ui.adapters.OnClickListener
import com.example.moviefindefapd.ui.view_models.PopularViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    var bindingclass: FragmentSearchBinding? = null

    private lateinit var sharedViewModel: SharedViewModel
    private val searchViewModel: PopularViewModel by viewModels()
    private val movieAdapter = FilmsAdapter()
    private val spanCount = 3
    private val marginSpan = 10
    private var searchJob: Job? = null
    private var searchEdit = "   "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingclass = FragmentSearchBinding.inflate(inflater, container, false)
        return bindingclass!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter.setOnClickListener(object : OnClickListener {
            override fun onClick(position: Int, model: Film) {
                val id = model.filmId.toString()
                val bundle = bundleOf("filmId" to id)
                findNavController().navigate(R.id.action_searchFragment_to_movieFragment, bundle)
            }
        })

        bindingclass!!.editSearch.setOnEditorActionListener { _, actionId, event ->
            if (
                actionId == EditorInfo.IME_ACTION_DONE
                || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val nameProduct = bindingclass!!.editSearch.text.toString()
                initProductsViewModel(nameProduct)
                return@setOnEditorActionListener true
            }
            false
        }

        bindingclass!!.editSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                if (searchQuery.toString().isNotEmpty()){
                    initProductsViewModel(searchQuery.toString())
                }
            }
        }

        prepareRecyclerView()
        initProductsViewModel(searchEdit)









        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.navController = findNavController()
        bindingclass!!.navigation.menu.findItem(R.id.search).isChecked = true
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
                R.id.settings -> {
                    sharedViewModel.navController?.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }


    }

    private fun initProductsViewModel(nameProduct: String) {
        searchViewModel.getSearchProduct(nameProduct)
        searchViewModel.observeSearchedLiveData()
            .observe(viewLifecycleOwner, Observer { movieList ->
                movieAdapter.setMovieList(movieList)
            })

//        searchViewModel.observeLoadingLiveData()
//            .observe(viewLifecycleOwner, Observer { isLoading ->
//                if (isLoading) {
//                    bindingSf.imageError.visibility = View.GONE
//                    bindingSf.messageError.visibility = View.GONE
//                    bindingSf.btnRepeat.visibility = View.GONE
//                    bindingSf.progressBar.visibility = View.VISIBLE
//                } else {
//                    bindingSf.progressBar.visibility = View.GONE
//                }
//            })

//        searchViewModel.observeErrorLiveData().observe(viewLifecycleOwner, Observer { error ->
//            if (error != null) {
//                val (errorCode, errorMessage) = error
//                if (errorMessage != null) {
//                    showError(bindingSf, R.string.error_message_server.toString())
//                } else {
//                    showSuccess(bindingSf)
//                }
//            }
//        })
    }

    private fun prepareRecyclerView() {
        bindingclass!!.recAll.apply {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = movieAdapter
        }
        bindingclass!!.recAll.addItemDecoration(SpaceItemDecoration(marginSpan))
    }
}
