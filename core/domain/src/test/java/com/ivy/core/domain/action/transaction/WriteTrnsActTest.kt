package com.ivy.core.domain.action.transaction

import android.graphics.Color
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.ivy.core.data.common.IvyColor
import com.ivy.core.domain.algorithm.accountcache.InvalidateAccCacheAct
import com.ivy.data.ItemIconId
import com.ivy.data.Sync
import com.ivy.data.SyncState
import com.ivy.data.Value
import com.ivy.data.account.Account
import com.ivy.data.account.AccountState
import com.ivy.data.attachment.Attachment
import com.ivy.data.attachment.AttachmentSource
import com.ivy.data.tag.Tag
import com.ivy.data.tag.TagState
import com.ivy.data.transaction.Transaction
import com.ivy.data.transaction.TransactionType
import com.ivy.data.transaction.TrnMetadata
import com.ivy.data.transaction.TrnPurpose
import com.ivy.data.transaction.TrnState
import com.ivy.data.transaction.TrnTime
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class WriteTrnsActTest{

    private lateinit var writeTrnsAct: WriteTrnsAct
    private lateinit var transactionDaoFake: TransactionDaoFake
    private lateinit var timeProviderFake: TimeProviderFake
    private lateinit var accountCacheDaoFake: AccountCacheDaoFake

    @BeforeEach
    fun setUp(){
        transactionDaoFake = TransactionDaoFake()
        timeProviderFake = TimeProviderFake()
        accountCacheDaoFake = AccountCacheDaoFake()
        writeTrnsAct = WriteTrnsAct(
            transactionDao = transactionDaoFake,
            trnsSignal = TrnsSignal(),
            timeProvider = timeProviderFake,
            invalidateAccCacheAct = InvalidateAccCacheAct(
                accountCacheDao = accountCacheDaoFake,
                timeProvider = timeProviderFake
            ),
            accountCacheDao = accountCacheDaoFake
        )
    }

    @Test
    fun `Test creating new expense transaction`() = runBlocking {
        val transactionId = UUID.randomUUID()
        val accountId = UUID.randomUUID()

        val account = Account(
            id = accountId,
            name = "Testing Account",
            currency = "PLN",
            color = Color.RED,
            icon = null,
            excluded = false,
            folderId = null,
            orderNum = 1.0,
            state = AccountState.Default,
            sync = Sync(state = SyncState.Synced, LocalDateTime.now())
        )

        val tag = Tag(
            UUID.randomUUID().toString(),
            Color.RED,
            "",
            2.0,
            TagState.Default,
            Sync(SyncState.Synced, LocalDateTime.now())
        )

        val attachment = Attachment(
            UUID.randomUUID().toString(),"","",AttachmentSource.Local, null, null, Sync(SyncState.Synced, LocalDateTime.now())
        )

        val metadata = TrnMetadata(null, null, null)

        val value = Value(50.0, "PLN")

        val transaction = Transaction(
            id = transactionId,
            account = account,
            type = TransactionType.Expense,
            value = value,
            category = null,
            time = TrnTime.Actual(actual = LocalDateTime.now()),
            title = null,
            description = null,
            state = TrnState.Default,
            purpose = TrnPurpose.Fee,
            tags = listOf(tag),
            attachments = listOf(attachment),
            metadata = metadata,
            sync = Sync(SyncState.Synced, LocalDateTime.now())
        )

        writeTrnsAct(WriteTrnsAct.Input.CreateNew(transaction))

        val cachedTransaction = transactionDaoFake.transactions.find{
            it.id == transaction.id.toString()
        }
        val cachedTag = transactionDaoFake.tags.find{
            it.tagId == tag.id
        }
        val cachedAttachment = transactionDaoFake.attachments.find{
            it.id == attachment.id
        }

        assertThat(cachedTransaction).isNotNull()
        assertThat(cachedTag).isNotNull()
        assertThat(cachedAttachment).isNotNull()

        assertThat(cachedTransaction?.type).isEqualTo(TransactionType.Expense)
    }
}