package ru.nightgoat.needdrummer.features.account.forgot_password

import dagger.hilt.android.lifecycle.HiltViewModel
import pro.krit.core.common.extensions.flatMapIfSuccess
import pro.krit.core.common.extensions.toNavigateResult
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.utilities.extentions.unsafeLazy
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val firebaseRepo: IFirebaseRepo,
    override val stringResources: IResourcesRepo
) : CoreAuthViewModel() {

    override val errorMessage: String by unsafeLazy {
        stringResources.rememberPassError
    }

    private suspend fun resetPassword(): AnyResult {
        return checkEmail().flatMapIfSuccess { enteredEmail ->
            firebaseRepo.resetPassword(enteredEmail).flatMapIfSuccess {
                ForgotPasswordFragmentDirections.showLoginFragment().toNavigateResult()
            }
        }
    }

    override fun onRegisterBtnClicked() {
        doWhileLoadingInNewCoroutine {
            resetPassword()
        }
    }
}