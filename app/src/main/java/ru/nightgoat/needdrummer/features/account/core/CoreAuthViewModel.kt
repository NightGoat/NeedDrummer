package ru.nightgoat.needdrummer.features.account.core

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.ErrorResult
import ru.nightgoat.needdrummer.models.states.ErrorType
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo

abstract class CoreAuthViewModel: CoreViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    abstract val errorMessage: String

    abstract val stringResources: IResourcesRepo

    fun checkPassword(): SResult<String> {
        return password.value?.let { enteredPassword ->
            if (enteredPassword.isNotEmpty()) {
                SResult.Success(enteredPassword)
            } else {
                getAuthError(type = ErrorType.EMPTY_PASSWORD)
            }
        } ?: getAuthError()
    }

    fun checkEmail(): SResult<String> {
        return email.value?.let { enteredEmail ->
            if (enteredEmail.isNotEmpty()) {
                SResult.Success(enteredEmail)
            } else {
                getAuthError(type = ErrorType.EMPTY_EMAIL)
            }
        } ?: getAuthError()
    }

    fun getAuthError(
        message: String = errorMessage,
        type: ErrorType = ErrorType.ORDINARY
    ): ErrorResult = getError(message, type)

    abstract fun onRegisterBtnClicked()
}