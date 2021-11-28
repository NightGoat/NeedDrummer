package ru.nightgoat.needdrummer.features.account.forgot_password

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.databinding.FragmentRegisterBinding
import ru.nightgoat.needdrummer.features.account.core.CoreAuthFragment

@AndroidEntryPoint
class ForgotPasswordFragment: CoreAuthFragment<FragmentRegisterBinding, ForgotPasswordViewModel>() {

    override val viewModel: ForgotPasswordViewModel by viewModels()

    override val layoutId = R.layout.fragment_forgot_password
}