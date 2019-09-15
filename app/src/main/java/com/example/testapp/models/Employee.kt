package com.example.testapp.models

class Employee constructor(var name: String, var age: Int, var phone: String, var gender: Gender) {}

enum class Gender {
    MALE, FEMALE
}