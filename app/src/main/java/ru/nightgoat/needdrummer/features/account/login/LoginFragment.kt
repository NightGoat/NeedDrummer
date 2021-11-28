package ru.nightgoat.needdrummer.features.account.login

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.databinding.FragmentLoginBinding
import ru.nightgoat.needdrummer.features.account.core.CoreAuthFragment

@AndroidEntryPoint
class LoginFragment : CoreAuthFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_login
}