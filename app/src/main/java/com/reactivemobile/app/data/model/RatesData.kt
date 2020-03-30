package com.reactivemobile.app.data.model

import java.util.*

data class RatesData(val baseCurrency: String, val rates: SortedMap<String, Double>)