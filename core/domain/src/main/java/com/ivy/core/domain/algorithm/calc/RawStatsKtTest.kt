package com.ivy.core.domain.algorithm.calc

import com.ivy.core.domain.algorithm.calc.data.RawStats
import com.ivy.core.persistence.algorithm.calc.CalcTrn
import com.ivy.data.CurrencyCode
import com.ivy.data.transaction.TransactionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class RawStatsKtTest{

    @Test
    fun calculatingRawStats(){
        val firstTrans = Instant.now()
        val secondTrans = Instant.now().plusSeconds(1)
        val thirdTrans = Instant.now().plusSeconds(2)
        val listOfTrans = listOf<CalcTrn>(
            CalcTrn(amount = 10.0, currency = "GBP", TransactionType.Income, firstTrans),
            CalcTrn(amount = 12.0, currency = "GBP", TransactionType.Expense, secondTrans),
            CalcTrn(amount = 9999.0, currency = "PLN", TransactionType.Income, thirdTrans)
        )

        val actualResult = rawStats(listOfTrans)

        val expectedResult = RawStats(
            mapOf("GBP" to 10.0, "PLN" to 9999.0),
            mapOf("GBP" to 12.0),
            2,
            1,
            thirdTrans
        )

        assertEquals(expectedResult, actualResult)
    }
}