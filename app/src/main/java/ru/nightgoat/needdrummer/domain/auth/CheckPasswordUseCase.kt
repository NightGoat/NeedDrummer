package ru.nightgoat.needdrummer.domain.auth

import com.rasalexman.sresult.common.extensions.orError
import com.rasalexman.sresult.data.dto.SResult
import com.rasalexman.sresult.domain.IUseCase
import ru.nightgoat.needdrummer.models.states.AuthFailure
import ru.nightgoat.needdrummer.models.util.Password
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject

class CheckPasswordUseCase @Inject constructor(
    private val stringResources: IStringResources
) : ICheckPasswordUseCase {
    override suspend fun invoke(data: Password?): SResult<Password> {
        return data?.let { enteredPassword ->
            if (enteredPassword.value.isNotEmpty()) {
                SResult.Success(enteredPassword)
            } else {
                AuthFailure.EmptyPassword
            }
        }.orError(
            stringResources.internalError
        )
    }
}

/** UseCase that checks is password null or empty */
interface ICheckPasswordUseCase : IUseCase.SingleInOut<Password?, SResult<Password>>