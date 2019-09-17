package com.example.testapp.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.models.Employee
import kotlinx.android.synthetic.main.employee_item.view.*

class EmployeeListAdapter(var employee_list: ArrayList<Employee>) : RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>(){

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employee_list[position]
        holder.view.employee_name_text.text = employee.name
        holder.view.employee_age_text.text = employee.age.toString()
        holder.view.employee_phone_text.text = employee.phone
        holder.view.employee_gender_text.text = employee.gender.toString()
    }

    override fun getItemCount(): Int = employee_list.size

    fun update(model_list: ArrayList<Employee>){
        employee_list = model_list
        notifyDataSetChanged()
    }
}