package com.reactivemobile.app.ui.main

import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.ui.base.BaseContract

class MainContract {
    interface View : BaseContract.View {
        fun showCv(cv: CV)
        fun showError()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun fetchCv()
    }
}