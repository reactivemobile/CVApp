package com.reactivemobile.app.data.remote

import com.reactivemobile.app.data.model.CV
import retrofit2.http.GET

interface NetworkService {
    @GET("reactivemobile/e5f5b4be6f4fe3392e6bca3946242102/raw/c433c721cac7b405798adae74395d730549c3b2b/cv.json")
    suspend fun getCV(): CV
}