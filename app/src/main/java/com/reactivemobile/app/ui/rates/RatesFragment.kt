package com.reactivemobile.app.ui.rates

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
import com.reactivemobile.app.ui.rates.adapter.RatesAdapter
import com.reactivemobile.app.ui.rates.viewmodel.RatesViewModel
import com.reactivemobile.app.ui.rates.viewmodel.RatesViewModelFactory
import com.reactivemobile.app.util.FlagMapper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rates_fragment.*
import javax.inject.Inject

class RatesFragment : Fragment() {
    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var viewModelFactory: RatesViewModelFactory

    @Inject
    lateinit var flagMapper: FlagMapper

    @Inject
    lateinit var picasso: Picasso

    private lateinit var adapter: RatesAdapter

    companion object {
        fun newInstance() = RatesFragment()
        const val TAG = "CvFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rates_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: RatesViewModel by activityViewModels { viewModelFactory }
        model.fetchRates()
        setupView(model)
    }

    private fun setupView(model: RatesViewModel) {
        model.rates.observe(viewLifecycleOwner, Observer {
            if (::adapter.isInitialized) {
                adapter.rates = it.rates
            } else {
                adapter = RatesAdapter(it.rates, flagMapper, picasso)
                recycler_view.adapter = adapter
            }
        })

        model.loading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        model.error.observe(viewLifecycleOwner, Observer {
            showMessage(getString(R.string.error_loading_data))
        })

//        get_cv.setOnClickListener {
//            model.fetchRates()
//        }
    }

    private fun showLoading(show: Boolean) {
        loading_view.visibility = if (show) VISIBLE else GONE
    }

    private fun showMessage(message: String) =
        Snackbar.make(main_view, message, Snackbar.LENGTH_SHORT).show()
}
