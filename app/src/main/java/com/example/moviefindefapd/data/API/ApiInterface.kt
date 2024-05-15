package com.example.moviefindefapd.data.API

import com.example.moviefindefapd.data.models.DescriprionMovies
import com.example.moviefindefapd.data.models.Movie
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @GET("/api/v2.2/films/top")
    fun getTopMovies(
        @Query("type") type: String = "TOP_100_POPULAR_FILMS",
        @Header("X-API-KEY") apiKey: String = "5a53d2cf-37d6-4d0f-a5a3-05e45e00acd8"
    ): Call<Movie>


    @GET("/api/v2.2/films/{id}")
    fun getMovieDetails(
        @Path("id") movieId: String,
        @Header("X-API-KEY") apiKey: String = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    ): Call<DescriprionMovies>

    @GET("/api/v2.1/films/search-by-keyword")
    fun getSearchedProducts(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1, // Значение по умолчанию равно 1
        @Header("X-API-KEY") apiKey: String = "5a53d2cf-37d6-4d0f-a5a3-05e45e00acd8"
    ): Call<Movie>



    companion object {

        var BASE_URL = "https://kinopoiskapiunofficial.tech"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

}