package com.example.db_try04.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.db_try04.R
import com.example.db_try04.data.entity.Student

class StudentRecyclerViewAdapter(val studentList: MutableList<Student>):
    RecyclerView.Adapter<StudentRecyclerViewAdapter.StudentViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: StudentViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = studentList.size

    class StudentViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem)
    {
        val id_textview = viewItem.findViewById<TextView>(R.id.id_tv)
        val name_textview = viewItem.findViewById<TextView>(R.id.name_tv)
        val update_btn = viewItem.findViewById<AppCompatButton>(R.id.update_btn)
        val delete_btn = viewItem.findViewById<AppCompatButton>(R.id.delete_btn)
    }
}