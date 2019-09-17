package com.example.testapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.models.Employee
import com.example.testapp.utils.DatabaseHandler
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity(){

    var database = DatabaseHandler(this)
    lateinit var employees : ArrayList<Employee>
    lateinit var adapter: EmployeeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        list_recycler.layoutManager = LinearLayoutManager(this)

        employees = database.readData()

        if(employees.isNotEmpty()){
            empty_list_text.visibility = View.GONE
        }
        else{
            empty_list_text.visibility = View.VISIBLE
        }

        adapter = EmployeeListAdapter(employees)
        list_recycler.adapter = adapter

        add_new_btn_list_activity.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1){
            database = DatabaseHandler(this)
            employees = database.readData()
            adapter.update(employees)
            if(employees.isNotEmpty()){
                empty_list_text.visibility = View.GONE
            }
            else{
                empty_list_text.visibility = View.VISIBLE
            }
        }
    }
}
