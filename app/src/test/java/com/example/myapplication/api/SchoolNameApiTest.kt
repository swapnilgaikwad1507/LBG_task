package com.example.myapplication.api

import com.example.myapplication.Helper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SchoolNameApiTest {

    lateinit var mockWebServer : MockWebServer
    lateinit var schoolApi : RetrofitService

    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        schoolApi=Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }

    @Test
    fun testGetSchoolNames() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = schoolApi.getSchoolNames()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()!!.isEmpty())
    }

    @Test
    fun testGetMultipleSchoolNames() = runTest{
        val mockResponse = MockResponse()

        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = schoolApi.getSchoolNames()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body()!!.isEmpty())
        Assert.assertEquals(3, response.body()!!.size)
    }

    @Test
    fun testGet_SchoolName_Error() = runTest{
        val mockResponse = MockResponse()

        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val response = schoolApi.getSchoolNames()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun Down(){
        mockWebServer.shutdown()
    }
}