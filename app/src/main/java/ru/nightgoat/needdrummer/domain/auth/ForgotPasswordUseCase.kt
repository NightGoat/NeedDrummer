package ru.nightgoat.needdrummer.domain.auth

import pro.krit.core.common.extensions.flatMapIfSuccess
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.data.repos.IAuthRepo
import ru.nightgoat.needdrummer.domain.IUseCase
import ru.nightgoat.needdrummer.models.util.Email
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val checkEmailUseCase: ICheckEmailUseCase,
    private val authRepo: IAuthRepo
) : IForgotPasswordUseCase {
    override suspend fun invoke(param: Email?): AnyResult {
        return checkEmailUseCase.invoke(param).flatMapIfSuccess { emailValue ->
            authRepo.rememberPassword(emailValue)
        }
    }
}

/** UseCase that checks is email null or empty and then sends link to change password */
interface IForgotPasswordUseCase : IUseCase.InOut<Email?, AnyResult>