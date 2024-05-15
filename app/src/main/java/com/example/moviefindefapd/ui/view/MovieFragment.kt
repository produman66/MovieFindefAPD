package com.example.moviefindefapd.ui.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviefindefapd.databinding.FragmentMovieBinding
import com.example.moviefindefapd.ui.view_models.MovieViewModel

class MovieFragment : Fragment() {

    private var binding: FragmentMovieBinding? = null
    private var viewModel: MovieViewModel? = null
    private var filmId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filmId = arguments?.getString("filmId").toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { binding ->
            checkInternet(binding)

//            binding.btnRepeat.setOnClickListener {
//                checkInternet(binding)
//            }

        }

        binding!!.btnFloating.setOnClickListener{
            findNavController().popBackStack()
        }
    }


    private fun initViewModel(filmId: String) {
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel!!.loadData(filmId)
        viewModel!!.movieData.observe(viewLifecycleOwner, Observer { movieList ->
            binding!!.nameMovie.text = movieList.nameRu
            binding!!.descMovie.text = movieList.description
//            movieList.genres.get(0).genre.capitalize()
//            binding!!.textCountry.text = movieList.countries.get(0).country.capitalize()
//            binding!!.ganre.text = movieList.genres.get(0).genre.capitalize()
            Glide.with(requireContext()).load(movieList.posterUrl).into(binding!!.imageSlider)
        })

//        viewModel!!.observeLoadingLiveData().observe(viewLifecycleOwner, Observer { isLoading ->
//            if (isLoading) {
//                binding!!.progressCircular.visibility = View.VISIBLE
//                binding!!.textView3.visibility = View.GONE
//                binding!!.textView4.visibility = View.GONE
//            } else {
//                binding!!.progressCircular.visibility = View.GONE
//            }
//        })

        viewModel!!.observeErrorLiveData().observe(viewLifecycleOwner, Observer { error ->
            if (error != null) {
                val (errorCode, errorMessage) = error
                if (errorMessage != null) {
//                    showError(binding!!, "Ошибка в запросе к серверу")
                } else {
//                    showSuccess(binding!!)
                }
            }
        })
    }


//    private fun showError(binding: FragmentMovieBinding, errorMessage: String) {
//        binding.imagePoster.visibility = View.GONE
//        binding.nameMovie.visibility = View.GONE
//        binding.descMovie.visibility = View.GONE
//        binding.textCountry.visibility = View.GONE
//        binding.ganre.visibility = View.GONE
//        binding.textView3.visibility = View.GONE
//        binding.textView4.visibility = View.GONE
//
//        binding.errorTextView.visibility = View.VISIBLE
//        binding.errorTextView.text = errorMessage
//        binding.imageNetworkError.visibility = View.VISIBLE
//        binding.btnRepeat.visibility = View.VISIBLE
//    }


//    private fun showSuccess(binding: FragmentMovieBinding) {
//        binding.imagePoster.visibility = View.VISIBLE
//        binding.nameMovie.visibility = View.VISIBLE
//        binding.descMovie.visibility = View.VISIBLE
//        binding.textCountry.visibility = View.VISIBLE
//        binding.ganre.visibility = View.VISIBLE
//        binding.textView3.visibility = View.VISIBLE
//        binding.textView4.visibility = View.VISIBLE
//
//        binding.errorTextView.visibility = View.GONE
//        binding.imageNetworkError.visibility = View.GONE
//        binding.btnRepeat.visibility = View.GONE
//    }


    private fun checkInternet(binding: FragmentMovieBinding) {
        if (isInternetAvailable(requireContext())) {
            initViewModel(filmId!!)
        } else {
//            showError(binding, "Произошла ошибка при загрузке данных,\nпроверьте подключение к сети!")
        }
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
}