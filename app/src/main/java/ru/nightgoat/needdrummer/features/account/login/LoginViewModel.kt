package ru.nightgoat.needdrummer.features.account.login

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.Failure
import ru.nightgoat.needdrummer.core.utilities.extentions.launchUITryCatch
import ru.nightgoat.needdrummer.core.utilities.orIfNull
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

class LoginViewModel : CoreViewModel() {

    @Inject
    lateinit var firebaseRepo: IFirebaseRepo

    @Inject
    lateinit var resources: IResourcesRepo

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun onLoginBtnClicked() {
        launchUITryCatch {
            email.value?.let { enteredEmail ->
                password.value?.let { enteredPassword ->
                    if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                        firebaseRepo.login(enteredEmail, enteredPassword).handleFailureOrRight {

                        }
                    } else {
                        handleFailure(Failure.WrongAuth)
                    }
                }.orIfNull {
                    handleFailure(Failure.AuthError)
                }
            }.orIfNull {
                handleFailure(Failure.AuthError)
            }
        }
    }
}