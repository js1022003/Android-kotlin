package com.example.viewbindingrv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewbindingrv.databinding.RecyclerviewItemBinding


class MainAdaptor(val taskList: Array<Task>):RecyclerView.Adapter<MainAdaptor.MainViewHolder>(){

    inner class MainViewHolder(val itemBinding:RecyclerviewItemBinding)
        :RecyclerView.ViewHolder(itemBinding.root){
            fun bindItem(task: Task){
                itemBinding.titleTv.text = task.title
                itemBinding.passwordTv.text = task.id_password

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindItem(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}