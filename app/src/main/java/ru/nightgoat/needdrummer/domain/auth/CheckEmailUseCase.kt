package ru.nightgoat.needdrummer.domain.auth

import pro.krit.core.common.extensions.orError
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.ErrorResult
import ru.nightgoat.needdrummer.domain.IUseCase
import ru.nightgoat.needdrummer.models.states.ErrorType
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val stringResources: IStringResources
) : ICheckEmailUseCase {
    override suspend fun invoke(param: Email?): SResult<Email> {
        return param?.let { enteredEmail ->
            if (enteredEmail.value.isNotEmpty()) {
                SResult.Success(enteredEmail)
            } else {
                ErrorResult(type = ErrorType.EMPTY_EMAIL)
            }
        }.orError(
            stringResources.internalError
        )
    }
}

/** UseCase that checks is email null or empty */
interface ICheckEmailUseCase : IUseCase.InOutSResult<Email?, Email>