package ru.nightgoat.needdrummer.domain.auth

import com.rasalexman.sresult.common.extensions.orError
import com.rasalexman.sresult.data.dto.SResult
import com.rasalexman.sresult.domain.IUseCase
import ru.nightgoat.needdrummer.models.states.AuthFailure
import ru.nightgoat.needdrummer.models.util.Name
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject

class CheckNameUseCase @Inject constructor(
    private val stringResources: IStringResources
) : ICheckNameUseCase {
    override suspend fun invoke(data: Name?): SResult<Name> {
        return data?.let { enteredEmail ->
            if (enteredEmail.value.isNotEmpty()) {
                SResult.Success(enteredEmail)
            } else {
                AuthFailure.EmptyName
            }
        }.orError(
            stringResources.internalError
        )
    }
}

/** UseCase that checks is name null or empty */
interface ICheckNameUseCase : IUseCase.SingleInOut<Name?, SResult<Name>>