package ru.nightgoat.needdrummer.features.account.login

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment: CoreFragment<FragmentLoginBinding, LoginViewModel>() {

    override val vm: LoginViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_login
}