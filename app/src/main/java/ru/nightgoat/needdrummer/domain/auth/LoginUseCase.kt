package ru.nightgoat.needdrummer.domain.auth

import com.rasalexman.sresult.common.extensions.flatMapIfSuccess
import com.rasalexman.sresult.common.extensions.navigateToResult
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.domain.IUseCase
import ru.nightgoat.needdrummer.data.repos.IAuthRepo
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
    override suspend fun invoke(data: Pair<Email?, Password?>): AnyResult {
        val (email, password) = data
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
interface ILoginUseCase : IUseCase.SingleInOut<Pair<Email?, Password?>, AnyResult>