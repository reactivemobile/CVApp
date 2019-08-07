package com.reactivemobile.app.ui.main

import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.data.remote.Repository
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(private val repository: Repository) : MainContract.Presenter {

    private lateinit var mainView: MainContract.View

    private var compositeDisposable = CompositeDisposable()

    override fun fetchCv() {
        compositeDisposable.add(
            handleLoading(mainView, repository.getCV())
                .subscribe(this::showCv, this::showError)
        )
    }

    private fun showError(throwable: Throwable?) {
        mainView.hideLoading();
        throwable?.printStackTrace()
        mainView.showError()
    }

    override fun attach(view: MainContract.View) {
        mainView = view
    }

    override fun viewReady() {
        loadData()
    }

    private fun loadData() {
        if (repository.getC() !=null) {
            showCv(repository.cachedCV!!)
        }
    }

    private fun showCv(cv: CV) {
        mainView.showCv(cv)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}