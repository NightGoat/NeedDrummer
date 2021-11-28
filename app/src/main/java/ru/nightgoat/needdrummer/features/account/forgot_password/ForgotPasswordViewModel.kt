package ru.nightgoat.needdrummer.features.account.forgot_password

import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.data.dto.ISEvent
import com.rasalexman.sresultpresentation.extensions.onEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nightgoat.kextensions.unsafeLazy
import ru.nightgoat.needdrummer.domain.auth.IForgotPasswordUseCase
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.toEmail
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: IForgotPasswordUseCase
) : CoreAuthViewModel() {

    override val resultLiveData by unsafeLazy {
        onEvent<ForgotPasswordEvent, AnyResult> { event ->
            emit(loadingResult())
            emit(forgotPasswordUseCase.invoke(event.email))
        }
    }

    private fun resetPassword() {
        val email = email.value?.toEmail()
        processEvent(ForgotPasswordEvent(email))
    }

    fun onRememberPasswordButtonClicked() {
        resetPassword()
    }

    inner class ForgotPasswordEvent(val email: Email?) : ISEvent
}