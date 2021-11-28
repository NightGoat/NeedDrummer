package ru.nightgoat.needdrummer.providers

import android.content.Context
import androidx.annotation.StringRes
import com.rasalexman.sresult.common.extensions.unsafeLazy
import ru.nightgoat.needdrummer.R
import javax.inject.Inject

class StringResources @Inject constructor(
    private val context: Context
) : IStringResources {
    override val authError by lazyString(R.string.auth_error)
    override val registerError by lazyString(R.string.register_error)
    override val rememberPassError by lazyString(R.string.remember_pass_error)
    override val wrongEmailOrPass by lazyString(R.string.wrong_email_or_pass)
    override val internalError by lazyString(R.string.wrong_email_or_pass)

    private fun lazyString(@StringRes stringId: Int) = unsafeLazy {
        context.getString(stringId)
    }
}

interface IStringResources {
    val authError: String
    val registerError: String
    val rememberPassError: String
    val wrongEmailOrPass: String
    val internalError: String
}