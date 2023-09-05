package com.ivy.core.domain.action.transaction

import arrow.core.valid
import com.ivy.core.persistence.algorithm.accountcache.AccountCacheDao
import com.ivy.core.persistence.algorithm.accountcache.AccountCacheEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.Instant

class AccountCacheDaoFake : AccountCacheDao {

    var accountCache = MutableStateFlow<List<AccountCacheEntity>>(emptyList())
    override fun findAccountCache(accountId: String): Flow<AccountCacheEntity?> {
        return accountCache.map {
            it.find {
                it.accountId == accountId
            }
        }
    }

    override suspend fun findTimestampById(accountId: String): Instant? {
        return accountCache.map {
            it.find {
                it.accountId == accountId
            }
        }.first()?.timestamp
    }

    override suspend fun save(cache: AccountCacheEntity) {
        accountCache.value += cache
    }

    override suspend fun delete(accountId: String) {
        val account = accountCache.value.find{
            it.accountId == accountId
        } ?: return
        accountCache.value -= account
    }

    override suspend fun deleteAll() {
        accountCache.update { emptyList() }
    }
}