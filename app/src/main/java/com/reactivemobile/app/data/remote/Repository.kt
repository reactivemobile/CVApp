package com.reactivemobile.app.data.remote

import com.reactivemobile.app.data.model.CV
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val service: NetworkService) {

    var cachedCV: CV? = null

    fun getCV(): Single<CV> {
        return service.getCV()
            .subscribeOn(Schedulers.io())
            .doAfterSuccess { list -> cacheCV(list) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun cacheCV(cv: CV?) {
        cv?.let {
            cachedCV = cv
        }
    }

    fun getC(): CV? {
        return cachedCV
    }
}
