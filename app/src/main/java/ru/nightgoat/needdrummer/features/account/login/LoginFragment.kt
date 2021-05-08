package ru.nightgoat.needdrummer.features.account.login

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.databinding.FragmentLoginBinding
import ru.nightgoat.needdrummer.models.states.ErrorType

@AndroidEntryPoint
class LoginFragment: CoreFragment<FragmentLoginBinding, LoginViewModel>() {

    override val vm: LoginViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_login

    override fun handleError(error: SResult.ErrorResult) {
        when (error.type) {
            ErrorType.EMPTY_EMAIL -> {
                binding?.emailIl?.error = getString(R.string.enter_email)
            }
            ErrorType.EMPTY_PASSWORD -> {
                binding?.passwordIl?.error = getString(R.string.enter_pass)
            }
            else -> super.handleError(error)
        }
    }
}