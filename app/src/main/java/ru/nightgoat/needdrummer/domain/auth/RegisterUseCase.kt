package ru.nightgoat.needdrummer.domain.auth

import com.rasalexman.sresult.common.extensions.flatMapIfSuccess
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.domain.IUseCase
import ru.nightgoat.needdrummer.data.repos.IAuthRepo
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.Name
import ru.nightgoat.needdrummer.models.util.Password
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val checkEmailUseCase: ICheckEmailUseCase,
    private val checkNameUseCase: ICheckNameUseCase,
    private val checkPasswordUseCase: ICheckPasswordUseCase,
    private val authRepo: IAuthRepo
) : IRegisterUseCase {
    override suspend fun invoke(data: Triple<Name?, Email?, Password?>): AnyResult {
        val (name, email, password) = data
        return checkEmailUseCase.invoke(email).flatMapIfSuccess { emailValue ->
            checkPasswordUseCase.invoke(password).flatMapIfSuccess { passwordValue ->
                checkNameUseCase.invoke(name).flatMapIfSuccess { nameValue ->
                    authRepo.register(emailValue, passwordValue, nameValue)
                }
            }
        }
    }
}

/** UseCase that checks is Email, Password and Name is null or empty, and then register user */
interface IRegisterUseCase : IUseCase.SingleInOut<Triple<Name?, Email?, Password?>, AnyResult>