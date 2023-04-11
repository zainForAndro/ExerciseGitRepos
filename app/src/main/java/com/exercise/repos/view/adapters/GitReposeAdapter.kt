package com.exercise.repos.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.exercise.repos.data.models.GitLocalData
import com.exercise.repos.databinding.ItemGitRepoBinding

class GitReposeAdapter() : RecyclerView.Adapter<GitReposeAdapter.GitRepoHolder>() {

    private var previousSelectedPosition = -1
    private val list = ArrayList<GitLocalData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoHolder {
        val binding = ItemGitRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GitRepoHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GitRepoHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            if (previousSelectedPosition != -1){
                if (previousSelectedPosition == holder.adapterPosition){
                    previousSelectedPosition = -1
                    notifyItemChanged(holder.adapterPosition)
                    return@setOnClickListener
                }
                notifyItemChanged(holder.adapterPosition)
                notifyItemChanged(previousSelectedPosition)
                previousSelectedPosition = holder.adapterPosition
            } else {
                previousSelectedPosition = holder.adapterPosition
                notifyItemChanged(previousSelectedPosition)

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateList(items: List<GitLocalData>){
        this.list.clear()
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    inner class GitRepoHolder(val binding: ItemGitRepoBinding) : ViewHolder(binding.root){
        fun bind(item: GitLocalData) {
            binding.description.text = item.description
            binding.fullName.text = item.full_name
            binding.name.text = item.name
            binding.language.text = item.language
            binding.rating.text = item.score
            Glide.with(binding.image.context).load(item.avatarUrl).into(binding.image)
            if (previousSelectedPosition == adapterPosition){
                binding.descriptionContainer.visibility = View.VISIBLE
            } else {
                binding.descriptionContainer.visibility = View.GONE
            }
        }
    }

}