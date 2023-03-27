package com.example.myapplication.api

import com.example.myapplication.model.SchoolNamesModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("f9bf-2cp4.json")
    suspend fun getSchoolNames() : Response<List<SchoolNamesModelItem>>
}