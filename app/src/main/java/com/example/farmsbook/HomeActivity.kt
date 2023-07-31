package com.example.farmsbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmsbook.apiService.Employee
import com.example.farmsbook.apiService.apiInterface
import com.example.farmsbook.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class HomeActivity : AppCompatActivity() {
    private lateinit var binding :ActivityHomeBinding
    private lateinit var adapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.111.220.195/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(apiInterface::class.java)
        api.getEmployees().enqueue(
            object : Callback<List<Employee>>{
                override fun onResponse(
                    call: Call<List<Employee>>,
                    response: Response<List<Employee>>
                ) {
                    Log.i("Failed",response.body().toString())
                    if(response.body()!=null){
                        adapter=MyAdapter(response.body()!!)
                        binding.recyclerView.also {
                            it.layoutManager=LinearLayoutManager(this@HomeActivity)
                            it.adapter=adapter
                        }
                    }
                }
                override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                    adapter= MyAdapter(emptyList())
                    binding.recyclerView.also {
                        it.layoutManager=LinearLayoutManager(this@HomeActivity)
                        it.adapter=adapter
                    }
                    Log.i("Failed",t.message.toString())
                }
            }
        )

    }
}