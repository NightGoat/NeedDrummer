package ru.nightgoat.needdrummer.features.account.register

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment: CoreFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val vm: RegisterViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_register
}