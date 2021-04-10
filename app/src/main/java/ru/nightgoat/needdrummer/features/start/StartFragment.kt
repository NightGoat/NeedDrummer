package ru.nightgoat.needdrummer.features.start

import androidx.fragment.app.viewModels
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.databinding.FragmentStartBinding

class StartFragment : CoreFragment<FragmentStartBinding, StartViewModel>() {

   override val vm: StartViewModel by viewModels()
   override fun getLayoutId(): Int = R.layout.fragment_start
}