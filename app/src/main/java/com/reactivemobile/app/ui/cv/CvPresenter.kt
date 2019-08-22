package com.reactivemobile.app.ui.cv

import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.data.remote.Repository
import io.reactivex.disposables.CompositeDisposable

class CvPresenter(private val repository: Repository) : CvContract.Presenter {

    private lateinit var view: CvContract.View

    private var compositeDisposable = CompositeDisposable()

    override fun fetchCv() {
        compositeDisposable.add(
            handleLoading(view, repository.getCV())
                .subscribe(this::showCv, this::showError)
        )
    }

    private fun showError(throwable: Throwable?) {
        view.hideLoading();
        throwable?.printStackTrace()
        view.showError()
    }

    override fun attach(view: CvContract.View) {
        this.view = view
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
        view.showCv(cv)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}