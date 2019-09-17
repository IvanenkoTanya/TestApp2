package com.example.testapp.views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.R
import com.example.testapp.models.Employee
import com.example.testapp.models.Gender
import com.example.testapp.utils.DatabaseHandler
import kotlinx.android.synthetic.main.new_record_activity.*

class NewRecordActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    var employee: Employee = Employee("", 0, "", Gender.FEMALE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_record_activity)

        var gender_spinner_items = arrayOf("MALE", "FEMALE")
        employee_gender_selector!!.setOnItemSelectedListener(this)
        val gender_spinner_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender_spinner_items)
        gender_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        employee_gender_selector!!.setAdapter(gender_spinner_adapter)

        save_btn.setOnClickListener {
            if(employee_name_edittext.text.toString().isNotEmpty()) {
                employee.name = employee_name_edittext.text.toString()
            }
            else {
                Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = Regex(pattern = """\d{2}""").matchEntire(employee_age_edittext.text)?.value
            if(age != null) {
                employee.age = age.toInt()
            }
            else {
                Toast.makeText(this, "Please enter the correct age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val phone = Regex(pattern = """\d{12}""").matchEntire(employee_phone_edittext.text)?.value
            if(phone != null) {
                employee.phone = phone
            }
            else {
                Toast.makeText(this, "Please enter the phone as a format: 380991346578", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val database = DatabaseHandler(this)
            if(database.insertData(employee)) {
                employee_name_edittext.text.clear()
                employee_age_edittext.text.clear()
                employee_phone_edittext.text.clear()
            }
        }
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        when(position){
            0 -> {
                employee.gender = Gender.MALE
            }
            1 -> {
                employee.gender = Gender.FEMALE
            }
            else -> {
                employee.gender = Gender.MALE
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}