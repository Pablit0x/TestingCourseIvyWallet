package com.ivy.core.domain.action.exchange

import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SyncExchangeRatesActTest {
    private lateinit var syncExchangeRatesAct: SyncExchangeRatesAct
    private lateinit var exchangeProviderFake: RemoteExchangeProviderFake
    private lateinit var exchangeRateDaoFake: ExchangeRateDaoFake

    @BeforeEach
    fun setUp(){
        exchangeProviderFake = RemoteExchangeProviderFake()
        exchangeRateDaoFake = ExchangeRateDaoFake()
        syncExchangeRatesAct = SyncExchangeRatesAct(
            exchangeProvider = exchangeProviderFake,
            exchangeRateDao = exchangeRateDaoFake
        )
    }


    @Test
    fun `Test sync exchange rate, negative values are ignored`() = runBlocking {
        syncExchangeRatesAct.invoke("USD")

        val usdRates = exchangeRateDaoFake.findAllByBaseCurrency("USD")
            .first {
                it.isNotEmpty()
            }

        val cadRate = usdRates.find { it.currency == "CAD" }

        assertThat(cadRate).isNull()
    }

    @Test
    fun `Test sync exchange rates, valid values saved`() = runBlocking<Unit> {
        syncExchangeRatesAct.invoke("USD")

        val usdRates = exchangeRateDaoFake.findAllByBaseCurrency("USD")
            .first{
                it.isNotEmpty()
            }

        val eurRate = usdRates.find { it.currency == "EUR" }
        val plnRate = usdRates.find { it.currency == "PLN" }

        assertThat(eurRate).isNotNull()
        assertThat(plnRate).isNotNull()
    }
}