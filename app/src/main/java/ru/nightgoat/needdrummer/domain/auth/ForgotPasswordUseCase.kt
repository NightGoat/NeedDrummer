package ru.nightgoat.needdrummer.domain.auth

import com.rasalexman.sresult.common.extensions.flatMapIfSuccess
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.domain.IUseCase
import ru.nightgoat.needdrummer.data.repos.IAuthRepo
import ru.nightgoat.needdrummer.models.util.Email
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val checkEmailUseCase: ICheckEmailUseCase,
    private val authRepo: IAuthRepo
) : IForgotPasswordUseCase {
    override suspend fun invoke(data: Email?): AnyResult {
        return checkEmailUseCase.invoke(data).flatMapIfSuccess { emailValue ->
            authRepo.rememberPassword(emailValue)
        }
    }
}

/** UseCase that checks is email null or empty and then sends link to change password */
interface IForgotPasswordUseCase : IUseCase.SingleInOut<Email?, AnyResult>