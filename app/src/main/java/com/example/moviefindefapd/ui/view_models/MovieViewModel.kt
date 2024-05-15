package com.example.moviefindefapd.ui.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviefindefapd.data.API.ApiInterface
import com.example.moviefindefapd.data.models.DescriprionMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private val _movieData = MutableLiveData<DescriprionMovies>()
    val movieData: LiveData<DescriprionMovies> get() = _movieData
    private var loadingLiveData = MutableLiveData<Boolean>()
    private var errorLiveData = MutableLiveData<Pair<Int, String?>>()

    fun loadData(id: String) {
        val apiInterface = ApiInterface.create().getMovieDetails(id)

        loadingLiveData.value = true

        apiInterface.enqueue(object : Callback<DescriprionMovies> {
            override fun onResponse(call: Call<DescriprionMovies>?, response: Response<DescriprionMovies>?) {
                loadingLiveData.value = false
                if (response!!.isSuccessful) {
                    _movieData.value = response?.body()
                    errorLiveData.value = Pair(response.code(), null)
                } else {
                    errorLiveData.value = Pair(response.code(), "Error loading data")
                    Log.e("json", "Error response: ${response.code()}, ${response.message()}")
                }

            }

            override fun onFailure(call: Call<DescriprionMovies>?, t: Throwable?) {
                Log.d("testLoads", "Moment Loading VM : ${t?.message}")
                loadingLiveData.value = false
                errorLiveData.value = Pair(0, t?.message)
            }
        })
    }


    fun observeErrorLiveData(): LiveData<Pair<Int, String?>> {
        return errorLiveData
    }

    fun observeLoadingLiveData(): LiveData<Boolean> {
        return loadingLiveData
    }
}