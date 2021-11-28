package ru.nightgoat.needdrummer.domain.auth

import android.util.Patterns
import com.rasalexman.sresult.common.extensions.flatMapIfSuccess
import com.rasalexman.sresult.common.extensions.orError
import com.rasalexman.sresult.data.dto.SResult
import com.rasalexman.sresult.domain.IUseCase
import ru.nightgoat.needdrummer.models.states.AuthFailure
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.toEmail
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val stringResources: IStringResources
) : ICheckEmailUseCase {
    override suspend fun invoke(data: Email?): SResult<Email> {
        return data?.let { enteredEmail ->
            val enteredEmailValue = enteredEmail.value
            enteredEmailValue.checkIsEmailNotEmpty().flatMapIfSuccess {
                it.checkIsEmailOk()
            }
        }.orError(
            stringResources.internalError
        )
    }

    private fun String.checkIsEmailOk(): SResult<Email> {
        val isOkEmail = Patterns.EMAIL_ADDRESS.toRegex().matches(this)
        return if (isOkEmail) {
            SResult.Success(this.toEmail())
        } else {
            AuthFailure.BadEmail
        }
    }

    private fun String.checkIsEmailNotEmpty(): SResult<String> {
        return if (this.isNotEmpty()) {
            SResult.Success(this)
        } else {
            AuthFailure.EmptyEmail
        }
    }
}

/** UseCase that checks is email null or empty */
interface ICheckEmailUseCase : IUseCase.SingleInOut<Email?, SResult<Email>>