package ru.nightgoat.needdrummer.features.account.core

import androidx.databinding.ViewDataBinding
import com.google.android.material.textfield.TextInputLayout
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.models.states.ErrorType

abstract class CoreAuthFragment<T : ViewDataBinding, S : CoreViewModel> : CoreFragment<T, S>() {

    private val emailView: TextInputLayout? = view?.findViewById(R.id.email_il)
    private val passwordView: TextInputLayout? = view?.findViewById(R.id.password_il)
    private val nameView: TextInputLayout? = view?.findViewById(R.id.name_il)

    override fun handleError(error: SResult.ErrorResult) {
        when (error.type) {
            ErrorType.EMPTY_NAME -> setNameError()
            ErrorType.EMPTY_EMAIL -> setEmailError()
            ErrorType.EMPTY_PASSWORD -> setPasswordEmail()
            else -> super.handleError(error)
        }
    }

    private fun setNameError() {
        nameView?.error = getString(R.string.enter_name)
    }

    private fun setEmailError() {
        emailView?.error = getString(R.string.enter_email)
    }

    private fun setPasswordEmail() {
        passwordView?.error = getString(R.string.enter_pass)
    }
}