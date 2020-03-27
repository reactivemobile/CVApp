package com.reactivemobile.app.data.remote

import com.reactivemobile.app.data.model.CV
import javax.inject.Inject

class Repository @Inject constructor(private val service: NetworkService) {

    private var cachedCV: CV? = null

    suspend fun getCV(): CV {
        val cv = service.getCV()
        cacheCv(cv)
        return cv
    }

    private fun cacheCv(cv: CV) {
        cachedCV = cv
    }
}
