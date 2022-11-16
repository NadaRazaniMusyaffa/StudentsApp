package com.example.studentapp.Adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.data.Student
import com.example.studentapp.databinding.ListItemBinding
import com.example.studentapp.databinding.StudentItemListBinding



class StudentListAdapter(private val onItemClicked : (Student) -> Unit) :
    ListAdapter<Student, StudentListAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(private var binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

                fun bind(student: Student) {
                    binding.apply {
                        nameTextView.text = student.name
                        idTextView.text = student.uid
                    }

                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            onItemClicked(current)
        }
        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }


}