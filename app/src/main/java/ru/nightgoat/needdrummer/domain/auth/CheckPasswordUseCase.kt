package ru.nightgoat.needdrummer.domain.auth

import pro.krit.core.common.extensions.orError
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.ErrorResult
import ru.nightgoat.needdrummer.domain.IUseCase
import ru.nightgoat.needdrummer.models.states.ErrorType
import ru.nightgoat.needdrummer.models.util.Password
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject

class CheckPasswordUseCase @Inject constructor(
    private val stringResources: IStringResources
) : ICheckPasswordUseCase {
    override suspend fun invoke(param: Password?): SResult<Password> {
        return param?.let { enteredPassword ->
            if (enteredPassword.value.isNotEmpty()) {
                SResult.Success(enteredPassword)
            } else {
                ErrorResult(type = ErrorType.EMPTY_PASSWORD)
            }
        }.orError(
            stringResources.internalError
        )
    }
}

/** UseCase that checks is password null or empty */
interface ICheckPasswordUseCase : IUseCase.InOutSResult<Password?, Password>