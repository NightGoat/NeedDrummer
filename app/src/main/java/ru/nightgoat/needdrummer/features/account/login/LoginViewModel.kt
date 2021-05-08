package ru.nightgoat.needdrummer.features.account.login

import dagger.hilt.android.lifecycle.HiltViewModel
import pro.krit.core.common.extensions.*
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.utilities.extentions.launchUITryCatch
import ru.nightgoat.needdrummer.core.utilities.extentions.unsafeLazy
import ru.nightgoat.needdrummer.features.account.AuthViewModel
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepo: IFirebaseRepo,
    override val stringResources: IResourcesRepo
) : AuthViewModel() {

    override val errorMessage: String by unsafeLazy {
        stringResources.authError
    }

    fun onLoginBtnClicked() {
        launchUITryCatch {
            doWhileLoading {
                getLogin()
            }
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

    fun onRegisterBtnClicked() {
        goTo(direction = LoginFragmentDirections.showRegisterFragment())
    }

    fun onForgotPasswordBtnClicked() {
        goTo(direction = LoginFragmentDirections.showForgotPasswordFragment())
    }
}