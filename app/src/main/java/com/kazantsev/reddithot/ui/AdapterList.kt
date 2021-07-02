package com.kazantsev.reddithot.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kazantsev.reddithot.databinding.ItemPostBinding
import com.kazantsev.reddithot.model.Post

class AdapterList() :
    PagingDataAdapter<Post, AdapterList.RecyclerItemViewHolder>(DataDiffCallback) {

    private lateinit var bindingItem: ItemPostBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        bindingItem = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerItemViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class RecyclerItemViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Post?) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                data?.let {
                    with(binding) {
                        tvTitle.text = data.title
                        tvComment.text = data.num_comments.toString()
                        tvScore.text = data.score.toString()
                    }
                }
            }

        }
    }


    private object DataDiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.title == newItem.title && oldItem.score == newItem.score
                    && oldItem.num_comments == newItem.num_comments
        }

    }
}
