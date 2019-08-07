package com.reactivemobile.app.ui.base

import io.reactivex.Single

class BaseContract {

    interface Presenter<in T> {
        fun attach(view: T)
        fun viewReady()
        fun onDestroy()

        fun <S> handleLoading(view: View, single: Single<S>): Single<S> {
            return single.doOnSubscribe { view.showLoading() }.doAfterTerminate { view.hideLoading() }
        }
    }

    interface View {
        fun showLoading()
        fun hideLoading()
    }
}