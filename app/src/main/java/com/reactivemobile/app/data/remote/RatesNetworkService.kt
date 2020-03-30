package com.reactivemobile.app.data.remote

import com.reactivemobile.app.data.model.RatesData
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesNetworkService {
    @GET("api/android/latest")
    suspend fun getRates(@Query("baseCurrency") baseCurrency: String): RatesData
}