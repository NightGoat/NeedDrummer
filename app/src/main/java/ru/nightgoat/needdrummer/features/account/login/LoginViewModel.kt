package ru.nightgoat.needdrummer.features.account.login

import androidx.lifecycle.MutableLiveData
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.toNavigateResult
import com.rasalexman.sresult.common.extensions.unsafeLazy
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.data.dto.ISEvent
import com.rasalexman.sresultpresentation.extensions.onEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nightgoat.needdrummer.domain.auth.ILoginUseCase
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.Password
import ru.nightgoat.needdrummer.models.util.toEmail
import ru.nightgoat.needdrummer.models.util.toPassword
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: ILoginUseCase
) : CoreAuthViewModel() {

    override val resultLiveData by unsafeLazy {
        onEvent<LoginViewModelEvent, AnyResult> { event ->
            emit(loadingResult())
            val result = when (event) {
                is LoginViewModelEvent.Register -> LoginFragmentDirections.showRegisterFragment()
                    .toNavigateResult()
                is LoginViewModelEvent.ForgotPassword -> LoginFragmentDirections.showForgotPasswordFragment()
                    .toNavigateResult()
                is LoginViewModelEvent.Login -> loginUseCase.invoke(event.loginPair)
            }
            emit(result)
        }
    }

    val password = MutableLiveData("")

    fun onLoginBtnClicked() {
        getLogin()
    }

    private fun getLogin() {
        val email = email.value?.toEmail()
        val password = password.value?.toPassword()
        val param = Pair(email, password)
        processEvent(LoginViewModelEvent.Login(param))
    }

    fun onRegisterBtnClicked() {
        processEvent(LoginViewModelEvent.Register)
    }

    fun onForgotPasswordBtnClicked() {
        processEvent(LoginViewModelEvent.ForgotPassword)
    }

    sealed class LoginViewModelEvent : ISEvent {
        data class Login(val loginPair: Pair<Email?, Password?>) : LoginViewModelEvent()
        object Register : LoginViewModelEvent()
        object ForgotPassword : LoginViewModelEvent()
    }
}