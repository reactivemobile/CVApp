package com.reactivemobile.app.ui.rates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reactivemobile.app.data.remote.Repository

class RatesViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RatesViewModel(repository) as T
    }
}