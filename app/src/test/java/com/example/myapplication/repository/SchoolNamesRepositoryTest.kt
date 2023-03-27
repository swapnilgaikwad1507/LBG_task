package com.example.myapplication.repository

import com.example.myapplication.api.NetworkResult
import com.example.myapplication.api.RetrofitService
import com.example.myapplication.model.SchoolNamesModelItem
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class SchoolNamesRepositoryTest {

    @Mock
    lateinit var apiSchoolNames : RetrofitService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetAllSchoolNames() = runTest{
        Mockito.`when`(apiSchoolNames.getSchoolNames()).thenReturn(Response.success(
            emptyList()))

        val sut = SchoolNamesRepository(apiSchoolNames)
        val result = sut.getAllSchoolNames()

        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun test_expect_SchoolNames() = runTest{

        val schoolList= listOf<SchoolNamesModelItem>(
            SchoolNamesModelItem("01M292","HENRY STREET SCHOOL FOR INTERNATIONAL STUDIES"),
            SchoolNamesModelItem("01M515","LOWER EAST SIDE PREPARATORY HIGH SCHOOL")
        )

        Mockito.`when`(apiSchoolNames.getSchoolNames()).thenReturn(Response.success(schoolList))

        val sut = SchoolNamesRepository(apiSchoolNames)
        val result = sut.getAllSchoolNames()

        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(2, result.data!!.size)
        Assert.assertEquals("01M292",result.data!![0].dbn)
    }

    @Test
    fun test_expect_Error_SchoolNames() = runTest{
        Mockito.`when`(apiSchoolNames.getSchoolNames()).thenReturn(Response.error(401,"Unauthorized".toResponseBody()))

        val sut = SchoolNamesRepository(apiSchoolNames)
        val result = sut.getAllSchoolNames()

        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("Something went wrong",result.message)
    }
}