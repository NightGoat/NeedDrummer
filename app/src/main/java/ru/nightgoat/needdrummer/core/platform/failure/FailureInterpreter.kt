package ru.nightgoat.needdrummer.core.platform.failure

import android.content.Context
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.platform.Failure

class FailureInterpreter (
    val context: Context
) : IFailureInterpreter {
    override fun getFailureDescription(failure: Failure): String {
        return when (failure) {
            is Failure.AuthError -> context.getString(R.string.auth_error)
            is Failure.WrongAuth -> context.getString(R.string.wrong_auth)
            is Failure.MessageFailure -> failure.message
            is Failure.ThrowableFailure -> failure.e.message.orEmpty()
        }
    }
}

interface IFailureInterpreter {
    fun getFailureDescription(failure: Failure): String
}