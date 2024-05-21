package com.example.moviefindefapd.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefindefapd.data.models.UserData
import com.example.moviefindefapd.databinding.ItemRecyclerBinding
import com.example.moviefindefapd.databinding.ItemUserRecyclerBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var userList = ArrayList<UserData>()
    private var onClickListener: OnClickListenerUser? = null


    fun setUserList(movieList: List<UserData>) {
        this.userList= movieList as ArrayList<UserData>
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemUserRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserRecyclerBinding.inflate(LayoutInflater.from(parent.context))
        binding.itemUserRecycler.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        holder.binding.itemUserRecycler.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, user)
            }
        }

        holder.binding.deleteUser.setOnClickListener{
            if (onClickListener != null) {
                onClickListener!!.onClick(position, user)
            }
        }

        holder.binding.nameUser.text = user.name.toString()

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setOnClickListener(onClickListener: OnClickListenerUser) {
        this.onClickListener = onClickListener
    }
}