package com.project.studentroomdatabase.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.studentroomdatabase.MyDatabase.Student
import com.project.studentroomdatabase.databinding.ItemStudentBinding

class StudentAdapter(
    private val listener: clickListener
) : RecyclerView.Adapter<StudentAdapter.StudentHolder>() {

    private val studentList = ArrayList<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        val studentItem = studentList[position]

        holder.name.text = studentItem.name
        holder.email.text = studentItem.email

        holder.itemView.setOnClickListener {
            listener.click(studentItem)
        }


    }


    override fun getItemCount(): Int {
        return studentList.size
    }
    fun setList(students: List<Student>){
        studentList.clear()
        studentList.addAll(students)
    }

    inner class StudentHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.ItemName
        val email = binding.ItemEmail

    }
    interface clickListener{
        fun click(student: Student)
    }
}
