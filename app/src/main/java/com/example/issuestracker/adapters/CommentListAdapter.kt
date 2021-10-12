package com.example.issuestracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.issuestracker.adapters.CommentListAdapter.MyViewHolder
import com.example.issuestracker.data.Comments
import com.example.issuestracker.data.Issue
import com.example.issuestracker.databinding.RvCommentItemLayoutBinding
import com.example.issuestracker.databinding.RvIssueItemLayoutBinding
import com.example.issuestracker.utils.RvItemClickListener

class CommentListAdapter(private val list: ArrayList<Comments>) : RecyclerView.Adapter<MyViewHolder>() {

    private var itemClickListener: RvItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RvCommentItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.item = list[position];
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClicked(position,holder.itemView)
        }
    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(val binding: RvCommentItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    fun setItemClickListener(listener:RvItemClickListener){
        this.itemClickListener=listener
    }
}