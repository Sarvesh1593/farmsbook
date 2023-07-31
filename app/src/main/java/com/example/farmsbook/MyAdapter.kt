package com.example.farmsbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farmsbook.apiService.Employee
import com.example.farmsbook.databinding.ItemBinding

class MyAdapter(private val employeeData : List<Employee>) :RecyclerView.Adapter<MyAdapter.Holder> (){
    private lateinit var binding:ItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        binding=ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return employeeData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = employeeData.get(position)
        holder.bind(model)
    }

    inner class Holder (binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(model:Employee){
            binding.apply {
                name.text=model.employeeName
                email.text=model.email
                phone.text=model.phoneNumber
                report.text=model.reportsTo
                Glide
                    .with(binding.root)
                    .load(model.profileImage)
                    .into(binding.imageview)
            }
        }
    }
}