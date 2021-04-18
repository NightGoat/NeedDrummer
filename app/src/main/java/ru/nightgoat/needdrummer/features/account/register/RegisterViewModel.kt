package ru.nightgoat.needdrummer.features.account.register

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.extentions.launchUITryCatch
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

class RegisterViewModel : CoreViewModel() {

    @Inject
    lateinit var firebaseRepo: IFirebaseRepo

    @Inject
    lateinit var resources: IResourcesRepo

    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private suspend fun register(): AnyResult {
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
        launchUITryCatch {
            register()
        }
    }
}