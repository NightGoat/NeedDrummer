package ru.nightgoat.needdrummer.features.account.forgot_password

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class ForgotPasswordFragment: CoreFragment<FragmentRegisterBinding, ForgotPasswordViewModel>() {

    override val vm: ForgotPasswordViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_forgot_password
}