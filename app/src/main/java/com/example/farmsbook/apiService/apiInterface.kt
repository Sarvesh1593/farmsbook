package com.example.farmsbook.apiService

import com.example.farmsbook.data.otpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface apiInterface {
    @GET("SMS/{phoneNumber}/AUTOGEN/VM%20-%20FARMSB")
    fun sendOTP(@Path("phoneNumber") phoneNumber: String): Call<otpResponse>

    @GET("SMS/VERIFY/{details}/{otp}")
    fun validateOTP(@Path("details") details: String, @Path("otp") otp: String): Call<otpResponse>

    @GET("employee")
    fun getEmployees():Call<List<Employee>>
}