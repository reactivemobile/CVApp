package com.reactivemobile.app.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.reactivemobile.app.App
import com.reactivemobile.app.R
import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.ui.main.adapter.MainAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(), MainContract.View {
    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    companion object {
        fun newInstance() = MainFragment()
        const val TAG = "MainFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
        mainPresenter.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        mainPresenter.viewReady()
    }

    private fun setupView() {
        get_cv.setOnClickListener {
            mainPresenter.fetchCv()
        }
    }

    override fun showCv(cv: CV) {
        recycler_view.adapter = MainAdapter(cv)
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
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    private fun showMessage(message: String) {
        Snackbar.make(main_view, message, Snackbar.LENGTH_SHORT).show()
    }
}
