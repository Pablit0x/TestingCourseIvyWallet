package com.ivy.core.domain.action.transaction

import com.ivy.common.time.provider.TimeProvider
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class TimeProviderFake : TimeProvider {
    override fun timeNow(): LocalDateTime {
        return LocalDateTime.of(2023, 1, 1, 12, 0)
    }

    override fun dateNow(): LocalDate {
        return LocalDate.of(2023, 1, 1)
    }

    override fun zoneId(): ZoneId {
        return ZoneId.of("UTC")
    }
}