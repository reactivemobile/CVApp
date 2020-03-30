package com.reactivemobile.app.util

interface FlagMapper {
    fun getFlagFromCurrency(currencyCode: String): String?
}