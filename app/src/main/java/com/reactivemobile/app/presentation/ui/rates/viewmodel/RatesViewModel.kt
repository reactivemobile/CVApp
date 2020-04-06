package com.reactivemobile.app.presentation.ui.rates.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reactivemobile.app.domain.FetchRatesUseCase
import com.reactivemobile.app.domain.RateData
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class RatesViewModel(private val fetchRatesUseCase: FetchRatesUseCase) : ViewModel() {
    val rates = MutableLiveData<RateData>()

    val loading = MutableLiveData<Boolean>()

    val error = MutableLiveData<Boolean>()

    lateinit var scheduledFuture: ScheduledFuture<*>

    private val scheduler = Executors.newScheduledThreadPool(1)

    fun startFetchingRates(baseCurrency: String = "EUR") {
        val timerTask = object : TimerTask() {
            override fun run() {
                fetchRates(baseCurrency)
            }
        }

        if (::scheduledFuture.isInitialized) {
            scheduledFuture.cancel(false)
        }

        scheduledFuture = scheduler.scheduleAtFixedRate(timerTask, 0L, 1, TimeUnit.SECONDS)
    }

    private fun fetchRates(baseCurrency: String) {
        Log.e("xxx", "fetchrates $baseCurrency")
        viewModelScope.launch {
            loading.postValue(true)

            val rateData = fetchRatesUseCase.getRates(baseCurrency)

            handleRateDataResponse(rateData)

            loading.postValue(false)
        }
    }

    fun setBaseAmount(amount: Editable?) {
        if (amount != null) {
            val amountNumber =
                try {
                    BigDecimal(if (amount.isEmpty()) 1.0 else amount.toString().toDouble())
                } catch (exception: NumberFormatException) {
                    BigDecimal(0)
                }

            val rateData = fetchRatesUseCase.setBaseAmount(amountNumber)
            handleRateDataResponse(rateData)
        }
    }

    private fun handleRateDataResponse(rateData: RateData) {
        if (rateData.error) {
            error.postValue(true)
        } else {
            rates.postValue(rateData)
        }
    }
}