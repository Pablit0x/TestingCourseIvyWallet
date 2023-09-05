package com.ivy.core.domain.action.exchange

import com.ivy.data.CurrencyCode
import com.ivy.data.ExchangeRatesMap
import com.ivy.data.exchange.ExchangeProvider
import com.ivy.exchange.RemoteExchangeProvider

class RemoteExchangeProviderFake : RemoteExchangeProvider {

    var ratesMap = mapOf(
        "USD" to mapOf(
            "EUR" to 0.91,
            "PLN" to 4.0,
            "CAD" to -3.0
        ),
        "EUR" to mapOf(
            "PLN" to 4.3,
            "USD" to 1.1,
            "CAD" to 1.4
        )
    )
    override suspend fun fetchExchangeRates(baseCurrency: CurrencyCode): RemoteExchangeProvider.Result {
        return RemoteExchangeProvider.Result(
            ratesMap = ratesMap[baseCurrency] as ExchangeRatesMap,
            provider = ExchangeProvider.Fawazahmed0
        )
    }
}