package ru.nightgoat.needdrummer.features.account.forgot_password

import androidx.lifecycle.MutableLiveData
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.extentions.launchUITryCatch
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import javax.inject.Inject

class ForgotPasswordViewModel : CoreViewModel() {

    @Inject
    lateinit var firebaseRepo: IFirebaseRepo

    val email = MutableLiveData("")

    private suspend fun resetPassword(): AnyResult {
        return email.value?.let { enteredEmail ->
                if (enteredEmail.isNotEmpty()) {
                    firebaseRepo.resetPassword(enteredEmail)
                } else {
                    SResult.ErrorResult.AuthError
                }
        } ?: SResult.ErrorResult.AuthError
    }

    fun onRegisterBtnClicked() {
        launchUITryCatch {
            handleResult(resetPassword())

        }
    }
}