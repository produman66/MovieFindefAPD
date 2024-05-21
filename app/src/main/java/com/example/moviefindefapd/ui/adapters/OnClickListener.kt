package com.example.moviefindefapd.ui.adapters

import com.example.moviefindefapd.data.models.DescriprionMovies
import com.example.moviefindefapd.data.models.Film
import com.example.moviefindefapd.data.models.UserData

interface OnClickListener {
    fun onClick(position: Int, model: Film)
}

interface OnClickListenerFilm {
    fun onClick(position: Int, model: DescriprionMovies)
}

interface OnClickListenerUser {
    fun onClick(position: Int, model: UserData)
}