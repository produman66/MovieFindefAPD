package com.example.moviefindefapd.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviefindefapd.data.models.Film
import com.example.moviefindefapd.databinding.ItemRecyclerBinding

class FilmsAdapter : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {
    private var movieList = ArrayList<Film>()
    private var onClickListener: OnClickListener? = null


    fun setMovieList(movieList: List<Film>) {
        this.movieList= movieList as ArrayList<Film>
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
        Glide.with(holder.itemView.context).load(film.posterUrlPreview).into(holder.binding.imageMovie)

        holder.binding.itemRecyclerPopular.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, film)
            }
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}