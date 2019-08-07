package com.reactivemobile.app.data.remote

import com.reactivemobile.app.data.model.CV
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService {
    @GET("reactivemobile/e5f5b4be6f4fe3392e6bca3946242102/raw/e1875ed62f9f59aaa6dbc38832f067d4d96d0193/cv.json")
    fun getCV(): Single<CV>
}