package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.adapters.SchoolNamesAdapter
import com.example.myapplication.api.NetworkResult
import com.example.myapplication.api.RetrofitHelper
import com.example.myapplication.api.RetrofitService
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.SchoolNamesRepository
import com.example.myapplication.viewModel.MyViewModelFactory
import com.example.myapplication.viewModel.SchoolNamesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: SchoolNamesViewModel
    val adapter = SchoolNamesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        viewModel.schools.observe(this, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    adapter.setCovidCasesAdapter(it.data!!)
                }
                is NetworkResult.Error ->{
                    binding.txtNoData.text = it.message
                }
                is NetworkResult.Loading ->{
                    binding.progressBar.isVisible = true
                }
            }

        })
    }

    private fun init() {
        val retrofitService = RetrofitHelper.getInstance().create(RetrofitService::class.java)
        viewModel = ViewModelProvider(this, MyViewModelFactory(SchoolNamesRepository(retrofitService))).get(
            SchoolNamesViewModel::class.java)
        binding.recyclerview.adapter = adapter
    }
}