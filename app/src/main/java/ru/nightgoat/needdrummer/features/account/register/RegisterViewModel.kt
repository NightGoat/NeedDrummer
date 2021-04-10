package ru.nightgoat.needdrummer.features.account.register

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.Either
import ru.nightgoat.needdrummer.core.platform.models.Failure
import ru.nightgoat.needdrummer.core.platform.orLeft
import ru.nightgoat.needdrummer.core.utilities.extentions.launchUITryCatch
import ru.nightgoat.needdrummer.models.User
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

class RegisterViewModel : CoreViewModel() {

    @Inject
    lateinit var firebaseRepo: IFirebaseRepo

    @Inject
    lateinit var resources: IResourcesRepo

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private suspend fun register(): Either<Failure, User> {
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
        launchUITryCatch {
            register()
        }
    }
}