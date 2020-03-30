package com.reactivemobile.app.ui.rates.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reactivemobile.app.data.model.RatesData
import com.reactivemobile.app.data.remote.Repository
import kotlinx.coroutines.launch

class RatesViewModel(private val repository: Repository) : ViewModel() {
    val rates = MutableLiveData<RatesData>()

    val loading = MutableLiveData<Boolean>()

    val error = MutableLiveData<Boolean>()

    val baseCurrency = MutableLiveData<String>()

    fun fetchRates(baseCurrency: String = "EUR") {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val ret = repository.getRates(baseCurrency)
                rates.postValue(ret)
            } catch (e: Exception) {
                error.postValue(true)
            }

            loading.postValue(false)
        }
    }
}