package com.reactivemobile.app.ui.cv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.reactivemobile.app.App
import com.reactivemobile.app.R
import com.reactivemobile.app.data.remote.Repository
import com.reactivemobile.app.ui.cv.adapter.CvAdapter
import com.reactivemobile.app.ui.cv.viewmodel.CvViewModel
import com.reactivemobile.app.ui.cv.viewmodel.CvViewModelFactory
import kotlinx.android.synthetic.main.cv_fragment.*
import javax.inject.Inject

class CvFragment : Fragment() {
    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var viewModelFactory: CvViewModelFactory

    private lateinit var adapter: CvAdapter

    companion object {
        fun newInstance() = CvFragment()
        const val TAG = "CvFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cv_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: CvViewModel by activityViewModels { viewModelFactory }

        setupView(model)
    }

    private fun setupView(model: CvViewModel) {
        model.cv.observe(viewLifecycleOwner, Observer {
            if (::adapter.isInitialized) {
                adapter.cv = it
            } else {
                adapter = CvAdapter(it)
                recycler_view.adapter = adapter
            }
        })

        model.loading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        model.error.observe(viewLifecycleOwner, Observer {
            showMessage(getString(R.string.error_loading_data))
        })

        get_cv.setOnClickListener {
            model.fetchCv()
        }
    }

    private fun showLoading(show: Boolean) {
        loading_view.visibility = if (show) VISIBLE else GONE
    }

    private fun showMessage(message: String) =
        Snackbar.make(main_view, message, Snackbar.LENGTH_SHORT).show()
}
