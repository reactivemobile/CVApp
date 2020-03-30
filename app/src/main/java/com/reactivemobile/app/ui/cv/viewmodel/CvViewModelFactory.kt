package com.reactivemobile.app.ui.cv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reactivemobile.app.data.remote.Repository

class CvViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CvViewModel(repository) as T
    }
}