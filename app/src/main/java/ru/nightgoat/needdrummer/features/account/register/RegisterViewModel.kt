package ru.nightgoat.needdrummer.features.account.register

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.domain.auth.IRegisterUseCase
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.models.util.toEmail
import ru.nightgoat.needdrummer.models.util.toName
import ru.nightgoat.needdrummer.models.util.toPassword
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: IRegisterUseCase
) : CoreAuthViewModel() {

    val name = MutableLiveData("")
    val password = MutableLiveData("")

    private suspend fun register(): AnyResult {
        val email = email.value?.toEmail()
        val password = password.value?.toPassword()
        val name = name.value?.toName()
        val param = Triple(name, email, password)
        return registerUseCase.invoke(param)
    }

    fun onRegisterBtnClicked() {
        doWhileLoadingInNewCoroutine {
            register()
        }
    }
}