package com.project.studentroomdatabase.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.studentroomdatabase.Adapter.StudentAdapter
import com.project.studentroomdatabase.MyDatabase.Student
import com.project.studentroomdatabase.MyDatabase.StudentDatabase
import com.project.studentroomdatabase.MyDatabase.StudentViewModel
import com.project.studentroomdatabase.MyDatabase.StudentViewModelFactory
import com.project.studentroomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentAdapter
    private lateinit var selectedStudent: Student
    private var isListItemClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        binding.MainBtnSave.setOnClickListener {
            if (isListItemClicked){
                updateStudentData()
                clearInput()
            }else{
                saveStudentData()
                clearInput()
            }

        }
        binding.MainBtnClear.setOnClickListener {
            if (isListItemClicked){
                deleteStudentData()
                clearInput()
            }else {
                clearInput()
            }
        }
        initRecyclerView()
    }

    private fun saveStudentData() {
        val name = binding.MainEtName.text.toString()
        val email = binding.MainEtEmail.text.toString()

        viewModel.insertStudent(Student(0, name, email))
    }

    private fun updateStudentData(){
        val name = binding.MainEtName.text.toString()
        val email = binding.MainEtEmail.text.toString()

        viewModel.updateStudent(Student(selectedStudent.id,name,email))

        binding.MainBtnSave.text = "Save"
        binding.MainBtnClear.text = "Clear"
        isListItemClicked = false
    }

    private fun deleteStudentData(){
        val name = binding.MainEtName.text.toString()
        val email = binding.MainEtEmail.text.toString()

        viewModel.deleteStudent(Student(selectedStudent.id,name,email))

        binding.MainBtnSave.text = "Save"
        binding.MainBtnClear.text = "Clear"
        isListItemClicked = false
    }

    private fun clearInput() {
        binding.MainEtName.setText("")
        binding.MainEtEmail.setText("")
    }

    private fun initRecyclerView() {
        binding.MainRv.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter(object : StudentAdapter.clickListener {
            override fun click(student: Student) {
                selectedStudent = student
                binding.MainBtnSave.text = "Update"
                binding.MainBtnClear.text = "Delete"
                isListItemClicked = true
                binding.MainEtName.setText(selectedStudent.name)
                binding.MainEtEmail.setText(selectedStudent.email)
            }
        })
        binding.MainRv.adapter = adapter
        displayStudentsList()
    }

    private fun displayStudentsList() {
        viewModel.students.observe(this, {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
}