package ru.nightgoat.needdrummer.domain.auth

import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.utilities.extentions.flatMapIfSuccess
import ru.nightgoat.needdrummer.core.utilities.extentions.navigateToResult
import ru.nightgoat.needdrummer.data.repos.IAuthRepo
import ru.nightgoat.needdrummer.domain.IUseCase
import ru.nightgoat.needdrummer.features.account.login.LoginFragmentDirections
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.Password
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val checkEmailUseCase: ICheckEmailUseCase,
    private val checkPasswordUseCase: ICheckPasswordUseCase,
    private val authRepo: IAuthRepo
) : ILoginUseCase {
    override suspend fun invoke(param: Pair<Email?, Password?>): AnyResult {
        val (email, password) = param
        return checkEmailUseCase.invoke(email).flatMapIfSuccess { emailValue ->
            checkPasswordUseCase.invoke(password).flatMapIfSuccess { passwordValue ->
                authRepo.login(emailValue, passwordValue).flatMapIfSuccess {
                    navigateToResult(
                        LoginFragmentDirections.showMainFragment()
                    )
                }
            }
        }
    }

}

/** UseCase that checks is email and password is null or empty, and then tries to login user */
interface ILoginUseCase : IUseCase.InOut<Pair<Email?, Password?>, AnyResult>