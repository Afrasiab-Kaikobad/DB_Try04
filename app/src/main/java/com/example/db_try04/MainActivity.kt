package com.example.db_try04

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.db_try04.data.InstitueDB
import com.example.db_try04.data.dao.StudentDao
import com.example.db_try04.data.entity.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var db: InstitueDB
    lateinit var addBtn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        db = InstitueDB.getDatabase(this)
        val studentDao = db.getStudentDao()

        addBtn = findViewById<FloatingActionButton>(R.id.add_btn)
        addBtn.setOnClickListener {
//            Toast.makeText(this,"Add button clicked",Toast.LENGTH_SHORT).show()
            addDialog(studentDao)
//            CoroutineScope(Dispatchers.IO).launch{
//                studentDao.Update(Student(id=2, name = "Ahmed"))
//            }

        }
    }
    fun addDialog(insertTool: StudentDao){
        val build = AlertDialog.Builder(this)
        build.setIcon(R.drawable.pencil_tool_svgrepo_com)
        val view = LayoutInflater.from(this).inflate(R.layout.add_person_view_layout,null)
        build.setView(view)

        build.setPositiveButton("Add"){
            dialog, which ->

            lifecycleScope.launch {
                val name = view.findViewById<TextInputEditText>(R.id.name_ET)
                insertTool.Insert(Student(name = name.text.toString()))
            }
        }
        build.setNegativeButton("Cancel",null)
        build.show()
    }
}