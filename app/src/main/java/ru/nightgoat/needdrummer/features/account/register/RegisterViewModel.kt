package ru.nightgoat.needdrummer.features.account.register

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.krit.core.common.extensions.flatMapIfSuccess
import pro.krit.core.common.extensions.toNavigateResult
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.extentions.unsafeLazy
import ru.nightgoat.needdrummer.features.account.core.CoreAuthViewModel
import ru.nightgoat.needdrummer.models.states.ErrorType
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRepo: IFirebaseRepo,
    override val stringResources: IResourcesRepo
) : CoreAuthViewModel() {

    override val errorMessage: String by unsafeLazy {
        stringResources.registerError
    }

    val name = MutableLiveData("")

    private suspend fun register(): AnyResult {
        return checkName().flatMapIfSuccess {
            checkEmail().flatMapIfSuccess { enteredEmail ->
                checkPassword().flatMapIfSuccess { enteredPassword ->
                    firebaseRepo.login(enteredEmail, enteredPassword).flatMapIfSuccess {
                        RegisterFragmentDirections.showLoginFragment().toNavigateResult()
                    }
                }
            }
        }
    }

    private fun checkName(): SResult<String> {
        return name.value?.let { enteredName ->
            if (enteredName.isNotEmpty()) {
                SResult.Success(enteredName)
            } else {
                getAuthError(type = ErrorType.EMPTY_NAME)
            }
        } ?: getAuthError()
    }

    override fun onRegisterBtnClicked() {
        doWhileLoadingInNewCoroutine {
            register()
        }
    }
}