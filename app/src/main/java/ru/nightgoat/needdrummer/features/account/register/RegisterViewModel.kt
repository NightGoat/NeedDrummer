package ru.nightgoat.needdrummer.features.account.register

import androidx.lifecycle.MutableLiveData
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.common.extensions.unsafeLazy
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.data.dto.ISEvent
import com.rasalexman.sresultpresentation.extensions.onEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nightgoat.needdrummer.domain.auth.IRegisterUseCase
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.models.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: IRegisterUseCase
) : CoreAuthViewModel() {

    override val resultLiveData by unsafeLazy {
        onEvent<RegisterEvent, AnyResult> { event ->
            emit(loadingResult())
            emit(registerUseCase.invoke(event.param))
        }
    }

    val name = MutableLiveData("")
    val password = MutableLiveData("")

    private fun register() {
        val email = email.value?.toEmail()
        val password = password.value?.toPassword()
        val name = name.value?.toName()
        val param = Triple(name, email, password)
        processEvent(RegisterEvent(param))
    }

    fun onRegisterBtnClicked() {
        register()
    }

    inner class RegisterEvent(val param: Triple<Name?, Email?, Password?>) : ISEvent
}