package com.ivy.core.domain.action.transaction

import android.graphics.Color
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
import java.time.LocalDateTime
import java.util.UUID

fun account() : Account{
    return Account(
        id = UUID.randomUUID(),
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
}

fun tag() : Tag {
    return Tag(
        UUID.randomUUID().toString(),
        Color.RED,
        "",
        2.0,
        TagState.Default,
        Sync(SyncState.Synced, LocalDateTime.now())
    )
}

fun attachment(associatedId: String) : Attachment{
    return Attachment(
        UUID.randomUUID().toString(), associatedId,"",
        AttachmentSource.Local, null, null, Sync(SyncState.Synced, LocalDateTime.now())
    )
}

fun transaction(id : UUID, account: Account) : Transaction{
    return Transaction(
        id = id,
        account = account,
        type = TransactionType.Expense,
        value = Value(50.0, "PLN"),
        category = null,
        time = TrnTime.Actual(actual = LocalDateTime.now()),
        title = null,
        description = null,
        state = TrnState.Default,
        purpose = TrnPurpose.Fee,
        tags = listOf(),
        attachments = listOf(),
        metadata = TrnMetadata(null, null, null),
        sync = Sync(SyncState.Synced, LocalDateTime.now())
    )
}