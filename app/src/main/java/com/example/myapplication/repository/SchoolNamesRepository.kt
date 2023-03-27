package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.NetworkResult
import com.example.myapplication.api.RetrofitService
import com.example.myapplication.model.SchoolNamesModelItem

class SchoolNamesRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllSchoolNames() : NetworkResult<List<SchoolNamesModelItem>>{

            val response = retrofitService.getSchoolNames()

             if(response.body()!=null){
                 return NetworkResult.Success(response.body()!!)
            }else if(response?.errorBody()!=null){
                 return NetworkResult.Error("Something went wrong")
            }else {
                 return NetworkResult.Error("Something went wrong")
            }
    }
}