package com.example.farmsbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.farmsbook.apiService.apiInterface
import com.example.farmsbook.data.otpResponse
import com.example.farmsbook.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var details:String
    private var OTP_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.goToBhaad.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
        binding.verifyButton.setOnClickListener {
            val phoneNumber = binding.phoneNumberEditText.text.toString().trim()
            if(phoneNumber.isNotEmpty()){
                sendOTP(phoneNumber)
            }else{
                Toast.makeText(this,"Please Enter a valid phone number",Toast.LENGTH_SHORT).show()
            }
        }
        binding.verifyOTPButton.setOnClickListener {
            val otp = binding.otpEditText.text.toString().trim()
            if(otp.isNotEmpty()){
                validateOtp(otp)
            }else{
                Toast.makeText(this,"Please enter the otp", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun sendOTP(phoneNumber: String) {
        Log.i("here phone",phoneNumber)
        val baseUrl= "https://2factor.in/API/V1/b02a2fb3-09aa-11ee-addf-0200cd936042/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(apiInterface::class.java)
        val call = apiService.sendOTP(phoneNumber)
        call.enqueue(object : Callback<otpResponse>{
            override fun onResponse(call: Call<otpResponse>, response: Response<otpResponse>) {
                Log.i("here details",response.body().toString())
                if(response.isSuccessful){
                    val otpResponse = response.body()
                    if(otpResponse?.Status == "Success"){
                        Toast.makeText(this@LoginActivity,"Otp sent to your phone ",Toast.LENGTH_SHORT).show()
                        details = response.body()?.Details.toString()

                        showOtpLayout()
                    }else {
                        Toast.makeText(this@LoginActivity, "Failed to send OTP1 ", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@LoginActivity, "Failed to send OTP", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<otpResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Failed to send OTP", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateOtp(otp: String) {
        val urlBase = "https://2factor.in/API/V1/b02a2fb3-09aa-11ee-addf-0200cd936042/"
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(apiInterface::class.java)
        Log.i("this",urlBase + " " + details + " " + otp)
        val call = apiService.validateOTP(details,otp)

        call.enqueue(object : Callback<otpResponse>{
            override fun onResponse(call: Call<otpResponse>, response: Response<otpResponse>) {
                Log.i("here res",response.body().toString())
                if(response.isSuccessful){
                    val otpResponse = response.body()
                    if(otpResponse?.Status == "Success"){
                        Toast.makeText(this@LoginActivity,"OTP verified Successfully",Toast.LENGTH_SHORT).show()
                        showSuccessLayout()
                        val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@LoginActivity,"OTP verification Failed1",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@LoginActivity,"OTP verification Failed",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<otpResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"OTP verification is Failed",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showOtpLayout() {
        binding.phoneNumberLayout.visibility = View.GONE
       binding.otpLayout.visibility = View.VISIBLE
    }
    private fun showSuccessLayout() {
        binding.otpLayout.visibility = View.GONE
        binding.successLayout.visibility = View.VISIBLE
    }

}