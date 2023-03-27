package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.NetworkResult
import com.example.myapplication.model.SchoolNamesModelItem
import com.example.myapplication.repository.SchoolNamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchoolNamesViewModel constructor(private val repository: SchoolNamesRepository)  : ViewModel() {

    private val _schoolNames = MutableLiveData<NetworkResult<List<SchoolNamesModelItem>>>()

    val schools:LiveData<NetworkResult<List<SchoolNamesModelItem>>>
    get() = _schoolNames

        init {
            getSchoolNames()
    }

    fun getSchoolNames(){
        viewModelScope.launch(Dispatchers.IO) {
            val result=repository.getAllSchoolNames()
            _schoolNames.postValue(result)
        }
    }
}