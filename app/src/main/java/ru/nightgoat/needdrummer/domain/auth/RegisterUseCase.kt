package ru.nightgoat.needdrummer.domain.auth

import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.utilities.extentions.flatMapIfSuccess
import ru.nightgoat.needdrummer.data.repos.IAuthRepo
import ru.nightgoat.needdrummer.domain.IUseCase
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
    override suspend fun invoke(param: Triple<Name?, Email?, Password?>): AnyResult {
        val (name, email, password) = param
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
interface IRegisterUseCase : IUseCase.InOut<Triple<Name?, Email?, Password?>, AnyResult>