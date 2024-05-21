package com.example.moviefindefapd.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviefindefapd.data.models.DescriprionMovies
import com.example.moviefindefapd.data.models.Film
import com.example.moviefindefapd.databinding.ItemRecyclerBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movieList: List<DescriprionMovies> = listOf()
    private var onClickListener: OnClickListenerFilm? = null


    fun setMovieList(movieList: List<DescriprionMovies>) {
        this.movieList= movieList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context))
//        binding.itemRecyclerPopular.layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.W,
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = movieList[position]
        Glide.with(holder.itemView.context).load(film.posterUrl).into(holder.binding.imageMovie)
        holder.binding.imageMovie.scaleType = ImageView.ScaleType.CENTER_CROP

        holder.binding.itemRecyclerPopular.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, film)
            }
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setOnClickListener(onClickListener: OnClickListenerFilm) {
        this.onClickListener = onClickListener
    }
}