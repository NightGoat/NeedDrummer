package ru.nightgoat.needdrummer.features.account.login

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
class LoginViewModel @Inject constructor(
    private val firebaseRepo: IFirebaseRepo,
    override val stringResources: IResourcesRepo
) : CoreAuthViewModel() {

    override val errorMessage: String by unsafeLazy {
        stringResources.authError
    }

    fun onLoginBtnClicked() {
        doWhileLoadingInNewCoroutine {
            getLogin()
        }
    }

    private suspend fun getLogin(): AnyResult {
        return checkEmail().flatMapIfSuccess { enteredEmail ->
            checkPassword().flatMapIfSuccess { enteredPassword ->
                firebaseRepo.login(enteredEmail, enteredPassword)
                    .flatMapIfSuccess {
                        LoginFragmentDirections.showMainFragment().toNavigateResult()
                    }
            }
        }
    }

    override fun onRegisterBtnClicked() {
        goTo(direction = LoginFragmentDirections.showRegisterFragment())
    }

    fun onForgotPasswordBtnClicked() {
        goTo(direction = LoginFragmentDirections.showForgotPasswordFragment())
    }
}