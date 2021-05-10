package ru.nightgoat.needdrummer.domain.auth

import pro.krit.core.common.extensions.orError
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.ErrorResult
import ru.nightgoat.needdrummer.domain.IUseCase
import ru.nightgoat.needdrummer.models.states.ErrorType
import ru.nightgoat.needdrummer.models.util.Name
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject

class CheckNameUseCase @Inject constructor(
    private val stringResources: IStringResources
) : ICheckNameUseCase {
    override suspend fun invoke(param: Name?): SResult<Name> {
        return param?.let { enteredEmail ->
            if (enteredEmail.value.isNotEmpty()) {
                SResult.Success(enteredEmail)
            } else {
                ErrorResult(type = ErrorType.EMPTY_NAME)
            }
        }.orError(
            stringResources.internalError
        )
    }
}

/** UseCase that checks is name null or empty */
interface ICheckNameUseCase : IUseCase.InOutSResult<Name?, Name>