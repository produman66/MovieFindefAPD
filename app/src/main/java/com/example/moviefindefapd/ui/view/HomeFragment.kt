package com.example.moviefindefapd.ui.view

import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.moviefindefapd.R
import com.example.moviefindefapd.data.models.Film
import com.example.moviefindefapd.ui.view_models.SharedViewModel
import com.example.moviefindefapd.databinding.FragmentHomeBinding
import com.example.moviefindefapd.ui.adapters.FilmsAdapter
import com.example.moviefindefapd.ui.adapters.OnClickListener
import com.example.moviefindefapd.ui.view_models.PopularViewModel

class HomeFragment : Fragment() {

    var bindingclass: FragmentHomeBinding? = null

    private lateinit var sharedViewModel: SharedViewModel

    private var viewModel: PopularViewModel? = null
    private var movieAdapter: FilmsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingclass = FragmentHomeBinding.inflate(inflater, container, false)
        return bindingclass!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.navController = findNavController()
        setupUI()
        bindingclass!!.navigation.menu.findItem(R.id.home).isChecked = true
        bindingclass!!.navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search -> {
                    sharedViewModel.navController?.navigate(R.id.searchFragment)
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

    private fun setupUI() {
        prepareRecyclerView()
        initViewModel()
        movieAdapter?.setOnClickListener(object : OnClickListener {
            override fun onClick(position: Int, model: Film) {
                val id = model.filmId.toString()
                val bundle = bundleOf("filmId" to id)
                findNavController().navigate(R.id.action_homeFragment2_to_movieFragment, bundle)
            }
        })
    }

    private fun prepareRecyclerView() {
        movieAdapter = FilmsAdapter()
        bindingclass!!.recyclerFilms.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = movieAdapter
        }
        bindingclass!!.recyclerFilms.addItemDecoration(SpaceItemDecoration(8))
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[PopularViewModel::class.java]
        viewModel!!.getPopularMovie()
        viewModel!!.observeMovieLiveData().observe(viewLifecycleOwner, Observer { movieList ->
            movieAdapter!!.setMovieList(movieList)
            val imageList = ArrayList<SlideModel>()
            imageList.add(SlideModel(movieList[0].posterUrl, ScaleTypes.CENTER_CROP))
            imageList.add(SlideModel(movieList[1].posterUrl, ScaleTypes.CENTER_CROP))
            imageList.add(SlideModel(movieList[2].posterUrl, ScaleTypes.CENTER_CROP))

            bindingclass!!.imageSlider.setImageList(imageList)
        })
//
//        viewModel!!.observeLoadingLiveData().observe(viewLifecycleOwner, Observer { isLoading ->
//            if (isLoading) {
//                binding!!.errorTextView.visibility = View.GONE
//                binding!!.imageNetworkError.visibility = View.GONE
//                binding!!.btnRepeat.visibility = View.GONE
//                binding!!.shimmerViewContainer.visibility = View.VISIBLE
//                binding!!.shimmerViewContainer.startShimmerAnimation();
//            } else {
//                binding!!.shimmerViewContainer.stopShimmerAnimation();
//                binding!!.shimmerViewContainer.visibility = View.GONE
//            }
//        })
//
//        viewModel!!.observeErrorLiveData().observe(viewLifecycleOwner, Observer { error ->
//            if (error != null) {
//                val (errorCode, errorMessage) = error
//                if (errorMessage != null) {
//                    showError(binding!!, "Ошибка в запросе к серверу")
//                } else {
//                    showSuccess(binding!!)
//                }
//            }
//        })
    }

//    private fun showError(binding: FragmentPopularBinding, errorMessage: String) {
//        binding.recyclerPopular.visibility = View.GONE
//        binding.errorTextView.visibility = View.VISIBLE
//        binding.errorTextView.text = errorMessage
//        binding.imageNetworkError.visibility = View.VISIBLE
//        binding.btnRepeat.visibility = View.VISIBLE
//    }
//
//    private fun showSuccess(binding: FragmentPopularBinding) {
//        binding.recyclerPopular.visibility = View.VISIBLE
//        binding.errorTextView.visibility = View.GONE
//        binding.imageNetworkError.visibility = View.GONE
//        binding.btnRepeat.visibility = View.GONE
//    }


//    private fun checkInternet(binding: FragmentPopularBinding){
//        if (isInternetAvailable(requireContext())) {
//            setupUI()
//        } else {
//            showError(binding, "Произошла ошибка при загрузке данных,\nпроверьте подключение к сети!")
//        }
//    }

}

private fun isInternetAvailable(context: Context): Boolean {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }

}




class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = space
        outRect.right = space
        outRect.left = space
        outRect.bottom = space
    }
}