package ru.nightgoat.needdrummer.features.account.login

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.extentions.launchUITryCatch
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import javax.inject.Inject

class LoginViewModel : CoreViewModel() {

    @Inject
    lateinit var firebaseRepo: IFirebaseRepo

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun onLoginBtnClicked() {
        launchUITryCatch {
            getLogin()
        }
    }

    private suspend fun getLogin(): AnyResult {
        return email.value?.let { enteredEmail ->
            password.value?.let { enteredPassword ->
                if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                    firebaseRepo.login(enteredEmail, enteredPassword)
                } else {
                    SResult.ErrorResult.AuthError
                }
            } ?: SResult.ErrorResult.AuthError
        } ?: SResult.ErrorResult.AuthError
    }

    fun onRegisterBtnClicked() {

    }

    fun onForgotPasswordBtnClicked() {

    }
}