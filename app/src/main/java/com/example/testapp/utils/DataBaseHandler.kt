package com.example.testapp.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.testapp.models.Employee
import com.example.testapp.models.Gender
import com.example.testapp.views.*

val DATABASE_NAME = "EmployeesDB"
val TABLE_NAME = "Employees"
val COL_ID = "id"
val COL_NAME = "name"
val COL_AGE = "age"
val COL_PHONE = "phone"
val COL_GENDER = "gender"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, 1) {
    override fun onCreate(database: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " VARCHAR(256), " +
                COL_AGE + " INTEGER, " +
                COL_PHONE + " VARCHAR(16), " +
                COL_GENDER + " VARCHAR(8))"
        database?.execSQL(createTableQuery)
    }

    fun insertData(employee: Employee) : Boolean {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, employee.name)
        contentValues.put(COL_AGE, employee.age)
        contentValues.put(COL_PHONE, employee.phone)
        contentValues.put(COL_GENDER, employee.gender.toString())
        val result = database.insert(TABLE_NAME, null, contentValues)
        return if (result == -1.toLong()) {
            Toast.makeText(context, "Something went wrong. Process is failed", Toast.LENGTH_SHORT).show()
            false
        } else {
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun readData(): ArrayList<Employee> {
        var list: ArrayList<Employee> = ArrayList()

        val db = this.readableDatabase
        val readDataQuery = "Select * from $TABLE_NAME"
        val result = db.rawQuery(readDataQuery, null)
        if(result.moveToFirst()){
            do {
                val name = result.getString(result.getColumnIndex(COL_NAME))
                val age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                val phone = result.getString(result.getColumnIndex(COL_PHONE))
                val gender = if (result.getString(result.getColumnIndex(COL_GENDER)) == "FEMALE") Gender.FEMALE else Gender.MALE
                val employee = Employee(name, age, phone, gender)
                list.add(employee)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}