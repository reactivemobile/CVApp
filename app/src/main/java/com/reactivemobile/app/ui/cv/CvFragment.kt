package com.reactivemobile.app.ui.cv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.reactivemobile.app.App
import com.reactivemobile.app.R
import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.ui.cv.adapter.CvAdapter
import kotlinx.android.synthetic.main.cv_fragment.*

import javax.inject.Inject

class CvFragment : Fragment(), CvContract.View {
    @Inject
    lateinit var presenter: CvContract.Presenter

    companion object {
        fun newInstance() = CvFragment()
        const val TAG = "CvFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cv_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
        presenter.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        presenter.viewReady()
    }

    private fun setupView() {
        get_cv.setOnClickListener {
            presenter.fetchCv()
        }
    }

    override fun showCv(cv: CV) {
        recycler_view.adapter = CvAdapter(cv)
    }

    override fun showError() {
        showMessage(getString(R.string.error_loading_data))
    }

    override fun showLoading() {
        loading_view.visibility = VISIBLE
    }

    override fun hideLoading() {
        loading_view.visibility = GONE
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun showMessage(message: String) {
        Snackbar.make(main_view, message, Snackbar.LENGTH_SHORT).show()
    }
}
