package com.example.testapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.models.Employee
import com.example.testapp.utils.DatabaseHandler
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.employee_item.view.*

class ListActivity : AppCompatActivity(){

    var db = DatabaseHandler(this)
    lateinit var employees : ArrayList<Employee>
    lateinit var adapter: employeeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        list_recycler.layoutManager = LinearLayoutManager(this)

        employees = db.readData()

        if(employees.isNotEmpty()){
            empty_list_text.visibility = View.GONE
        }
        else{
            empty_list_text.visibility = View.VISIBLE
        }

        adapter = employeeListAdapter(employees)
        list_recycler.adapter = adapter

        add_new_btn_list_activity.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1){
            db = DatabaseHandler(this)
            adapter.update(db.readData())
        }
    }
}


class employeeListAdapter(var employeeList: ArrayList<Employee>) : RecyclerView.Adapter<employeeListAdapter.ViewHolder>(){

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employeeList[position]
        holder.view.employee_name_text.text = employee.name
        holder.view.employee_age_text.text = employee.age.toString()
        holder.view.employee_phone_text.text = employee.phone
        holder.view.employee_gender_text.text = employee.gender.toString()
    }

    override fun getItemCount(): Int = employeeList.size

    fun update(modelList:ArrayList<Employee>){
        employeeList = modelList
        notifyDataSetChanged()
    }
}
