package ru.nightgoat.needdrummer.features.account.register

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.databinding.FragmentRegisterBinding
import ru.nightgoat.needdrummer.features.account.core.CoreAuthFragment

@AndroidEntryPoint
class RegisterFragment: CoreAuthFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()
    override val layoutId = R.layout.fragment_register
}