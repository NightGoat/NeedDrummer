package ru.nightgoat.needdrummer.features.account.login

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.domain.auth.ILoginUseCase
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.models.util.toEmail
import ru.nightgoat.needdrummer.models.util.toPassword
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: ILoginUseCase
) : CoreAuthViewModel() {

    val password = MutableLiveData("")

    fun onLoginBtnClicked() {
        doWhileLoadingInNewCoroutine {
            getLogin()
        }
    }

    private suspend fun getLogin(): AnyResult {
        val email = email.value?.toEmail()
        val password = password.value?.toPassword()
        val param = Pair(email, password)
        return loginUseCase.invoke(param)
    }

    fun onRegisterBtnClicked() {
        goTo(direction = LoginFragmentDirections.showRegisterFragment())
    }

    fun onForgotPasswordBtnClicked() {
        goTo(direction = LoginFragmentDirections.showForgotPasswordFragment())
    }
}