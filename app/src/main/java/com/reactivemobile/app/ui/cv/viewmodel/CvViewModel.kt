package com.reactivemobile.app.ui.cv.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.data.remote.Repository
import kotlinx.coroutines.launch

class CvViewModel(private val repository: Repository) : ViewModel() {
    val cv = MutableLiveData<CV>()

    val loading = MutableLiveData<Boolean>()

    val error = MutableLiveData<Boolean>()

    fun fetchCv() {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val ret = repository.getCV()
                cv.postValue(ret)
            } catch (e: Exception) {
                error.postValue(true)
            }

            loading.postValue(false)
        }
    }
}