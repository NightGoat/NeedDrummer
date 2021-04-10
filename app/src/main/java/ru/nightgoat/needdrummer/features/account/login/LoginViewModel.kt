package ru.nightgoat.needdrummer.features.account.login

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.Either
import ru.nightgoat.needdrummer.core.platform.models.Failure
import ru.nightgoat.needdrummer.core.platform.orLeft
import ru.nightgoat.needdrummer.core.utilities.extentions.launchUITryCatch
import ru.nightgoat.needdrummer.models.User
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import javax.inject.Inject

class LoginViewModel : CoreViewModel() {

    @Inject
    lateinit var firebaseRepo: IFirebaseRepo

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun onLoginBtnClicked() {
        launchUITryCatch {
            getLogin().handleFailureOrRight {

            }
        }
    }

    private suspend fun getLogin(): Either<Failure, User> {
        return email.value?.let { enteredEmail ->
            password.value?.let { enteredPassword ->
                if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                    firebaseRepo.login(enteredEmail, enteredPassword)
                } else {
                    Either.Left(Failure.WrongAuth)
                }
            }.orLeft(Failure.AuthError)
        }.orLeft(Failure.AuthError)
    }

    fun onRegisterBtnClicked() {

    }

    fun onForgotPasswordBtnClicked() {

    }
}