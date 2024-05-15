package com.example.moviefindefapd.ui.adapters

import com.example.moviefindefapd.data.models.Film

interface OnClickListener {
    fun onClick(position: Int, model: Film)
}