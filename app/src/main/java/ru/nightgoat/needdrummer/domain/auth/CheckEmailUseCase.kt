package ru.nightgoat.needdrummer.domain.auth

import android.util.Patterns
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.utilities.ErrorResult
import ru.nightgoat.needdrummer.core.utilities.extentions.flatMapIfSuccess
import ru.nightgoat.needdrummer.core.utilities.extentions.orError
import ru.nightgoat.needdrummer.domain.IUseCase
import ru.nightgoat.needdrummer.models.states.ErrorType
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.toEmail
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val stringResources: IStringResources
) : ICheckEmailUseCase {
    override suspend fun invoke(param: Email?): SResult<Email> {
        return param?.let { enteredEmail ->
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
            ErrorResult(type = ErrorType.BAD_EMAIL)
        }
    }

    private fun String.checkIsEmailNotEmpty(): SResult<String> {
        return if (this.isNotEmpty()) {
            SResult.Success(this)
        } else {
            ErrorResult(type = ErrorType.EMPTY_EMAIL)
        }
    }
}

/** UseCase that checks is email null or empty */
interface ICheckEmailUseCase : IUseCase.InOutSResult<Email?, Email>