package ru.nightgoat.needdrummer.features.account.core

import androidx.databinding.ViewDataBinding
import com.google.android.material.textfield.TextInputLayout
import com.rasalexman.sresult.data.dto.SResult
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.models.states.AuthFailure

abstract class CoreAuthFragment<T : ViewDataBinding, S : CoreViewModel> : CoreFragment<T, S>() {

    private val emailView: TextInputLayout?
        get() = view?.findViewById(R.id.email_il)

    private val passwordView: TextInputLayout?
        get() = view?.findViewById(R.id.password_il)

    private val nameView: TextInputLayout?
        get() = view?.findViewById(R.id.name_il)

    override fun onResultHandler(result: SResult<*>) {
        if (result is AuthFailure) {
            handleError(result)
        } else {
            super.onResultHandler(result)
        }
    }

    private fun handleError(error: AuthFailure) {
        when (error) {
            AuthFailure.EmptyName -> setNameError()
            AuthFailure.EmptyEmail -> setEmptyEmailError()
            AuthFailure.EmptyPassword -> setPasswordEmail()
            AuthFailure.BadEmail -> setWrongEmailError()
        }
    }

    private fun setNameError() {
        nameView?.error = getString(R.string.enter_name)
    }

    private fun setEmptyEmailError() {
        emailView?.error = getString(R.string.enter_email)
    }

    private fun setWrongEmailError() {
        emailView?.error = getString(R.string.wrong_email)
    }

    private fun setPasswordEmail() {
        passwordView?.error = getString(R.string.enter_pass)
    }
}