package com.example.farmsbook.apiService

data class Employee (
    val id : Int,
    val phoneNumber: String,
    val employeeName: String,
    val email: String,
    val reportsTo: String,
    val profileImage : String
)
