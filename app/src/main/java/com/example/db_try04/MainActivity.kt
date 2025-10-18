package com.example.db_try04

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.db_try04.adapter.StudentRecyclerViewAdapter
import com.example.db_try04.data.InstitueDB
import com.example.db_try04.data.dao.StudentDao
import com.example.db_try04.data.entity.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var studentList: MutableList<Student>
    lateinit var studentDao: StudentDao
    lateinit var addBtn : FloatingActionButton
    lateinit var recyclerview: RecyclerView
    lateinit var studentRecyclerAdapter: StudentRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        studentList = mutableListOf()

        /****************************************************
         * Initialize the UI components
         ****************************************************/

        addBtn = findViewById<FloatingActionButton>(R.id.add_btn)
        recyclerview = findViewById<RecyclerView>(R.id.recycleview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO,{setDataBase()})
            withContext(Dispatchers.IO,{updateStudentListFromDB()})
            updateRecyclerView()
        }


        /****************************************************
         * Event on UI components
         ****************************************************/

        addBtn.setOnClickListener {
            addDialog()
        }
    }

    fun setDataBase(){
        val db = InstitueDB.getDatabase(applicationContext)
        studentDao = db.getStudentDao()
    }
    suspend fun addStudentinDb(student: Student){
        studentDao.Insert(student)
    }

    suspend fun updateStudentListFromDB()
    {
        studentList.clear()
        studentList = studentDao.getAllStudent() as MutableList<Student>
    }
    fun updateRecyclerView(){
        studentRecyclerAdapter = StudentRecyclerViewAdapter(studentList,{updateDialog(it)})
        recyclerview.adapter = studentRecyclerAdapter
    }

    fun addDialog(){
        val build = AlertDialog.Builder(this)
        build.setIcon(R.drawable.pencil_tool_svgrepo_com)
        val view = LayoutInflater.from(this).inflate(R.layout.add_person_view_layout,null)
        build.setView(view)

        build.setPositiveButton("Add"){
            dialog, which ->
            CoroutineScope(Dispatchers.Main).launch {
                val student = Student(name = view.findViewById<TextInputEditText>(R.id.name_ET).text.toString())
                withContext(Dispatchers.IO,{
                    addStudentinDb(student)
                    updateStudentListFromDB()
                })
                updateRecyclerView()
            }

        }
        build.setNegativeButton("Cancel",null)
        build.show()
    }

    fun updateDialog(student:Student){
        val view = LayoutInflater.from(this).inflate(R.layout.update_student_view_item01,null)
        val idTv = view.findViewById<TextView>(R.id.change_id_tv)
        idTv.text = student.id.toString()
        val nameET = view.findViewById<TextInputEditText>(R.id.change_name_et)
        nameET.setText(student.name)
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(view)
        alertDialog.setPositiveButton("Update",{
            dialog, which ->

            CoroutineScope(Dispatchers.Main).launch{
                val updatedStudent = Student(idTv.text.toString().toInt(),nameET.text.toString())
                withContext(Dispatchers.IO, {
                    studentDao.Update(updatedStudent)
                    updateStudentListFromDB()
                })

                updateRecyclerView()
            }
        })
        alertDialog.setNegativeButton("Cancel",null)
        alertDialog.setTitle("Update Student")
        alertDialog.show()
    }
}