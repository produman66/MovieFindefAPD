package com.example.moviefindefapd.ui.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviefindefapd.data.API.ApiInterface
import com.example.moviefindefapd.data.models.Film
import com.example.moviefindefapd.data.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PopularViewModel : ViewModel() {
    private var movieLiveData = MutableLiveData<List<Film>>()

    private var searchedProductLiveData = MutableLiveData<List<Film>>()


    private var loadingLiveData = MutableLiveData<Boolean>()
    private var errorLiveData = MutableLiveData<Pair<Int, String?>>()
    fun getPopularMovie() {
        loadingLiveData.value = true
        ApiInterface.create().getTopMovies().enqueue(object  : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                loadingLiveData.value = false
                if (response.body()!=null){
                    movieLiveData.value = response.body()!!.films
                    errorLiveData.value = Pair(response.code(), null)
                    Log.d("new",response.body().toString())
                }
                else{
                    errorLiveData.value = Pair(response.code(), "Error loading data")
                    Log.d("new","Error loading data")
                }
            }
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("new",t.message.toString())
                loadingLiveData.value = false
                errorLiveData.value = Pair(0, t.message)
            }
        })
    }

    fun getSearchProduct(searchQuery: String){

        loadingLiveData.value = true

        ApiInterface.create().getSearchedProducts(searchQuery).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {


                if (response.isSuccessful) {
                    searchedProductLiveData.value = response.body()?.films
                    errorLiveData.value = Pair(response.code(), null)
                    Log.i("json", "Cool: ${response.body()}")
                } else {
                    errorLiveData.value = Pair(response.code(), "Error loading data")
                    Log.e("json", "Error response: ${response.code()}, ${response.message()}")
                }

                loadingLiveData.value = false
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
                loadingLiveData.value = false
                errorLiveData.value = Pair(0, t.message)
            }
        })
    }




    fun observeMovieLiveData() : LiveData<List<Film>> {
        return movieLiveData
    }

    fun observeErrorLiveData(): LiveData<Pair<Int, String?>> {
        return errorLiveData
    }

    fun observeLoadingLiveData(): LiveData<Boolean> {
        return loadingLiveData
    }

    fun observeSearchedLiveData() : LiveData<List<Film>> {
        return searchedProductLiveData
    }
}