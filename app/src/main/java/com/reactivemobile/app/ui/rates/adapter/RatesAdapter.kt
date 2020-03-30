package com.reactivemobile.app.ui.rates.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.reactivemobile.app.R
import com.reactivemobile.app.util.FlagMapper
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class RatesAdapter(
    rates: SortedMap<String, Double>,
    val flagMapper: FlagMapper,
    val picasso: Picasso
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var keys = mutableListOf<String>()

    var currencies = mutableListOf<Currency>()

    var rates = rates
        set(value) {
            field = value
            updateKeysIfChanged()
            notifyDataSetChanged()
        }

    init {
        updateKeysIfChanged()
    }

    private fun updateKeysIfChanged() {
        keys.clear()
        keys.addAll(ArrayList<String>(rates.keys))

        currencies.clear()

        keys.forEach { currencies.add(Currency.getInstance(it)) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HeaderViewHolder(inflate(parent, R.layout.header_list_item))
    }

    private fun inflate(parent: ViewGroup, @LayoutRes id: Int): View {
        return LayoutInflater.from(parent.context).inflate(id, parent, false)
    }

    override fun getItemCount(): Int {
        Log.e("XXX", "Keys= $keys")

        return keys.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currency = keys[position]
        val value = currencies.get(position)
        (holder as HeaderViewHolder).let {
            it.setCurrencyName(value.displayName)
            it.setCurrencyCode(value.currencyCode)
        }

    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val currencyCode = view.findViewById<TextView>(R.id.currency_code)
        private val currencyName = view.findViewById<TextView>(R.id.currency_name)
        private val currencyFlag = view.findViewById<ImageView>(R.id.flag_image)

        fun setCurrencyCode(code: String) {
            currencyCode.text = code
            val path =
                "https://github.com/transferwise/currency-flags/raw/master/src/flags/$code.png".toLowerCase(
                    Locale.ENGLISH
                )

            Log.e("XXX", "Path = $path")

            picasso.load(path).error(R.drawable.ic_error_outline_black_24dp)
                .into(currencyFlag)
        }

        fun setCurrencyName(text: String) {
            currencyName.text = text
        }
    }
}