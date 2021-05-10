package ru.nightgoat.needdrummer.features.account.forgot_password

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.domain.auth.IForgotPasswordUseCase
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.models.util.toEmail
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: IForgotPasswordUseCase
) : CoreAuthViewModel() {

    private suspend fun resetPassword(): AnyResult {
        val email = email.value?.toEmail()
        return forgotPasswordUseCase.invoke(email)
    }

    fun onRememberPasswordButtonClicked() {
        doWhileLoadingInNewCoroutine {
            resetPassword()
        }
    }
}