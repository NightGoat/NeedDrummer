package ru.nightgoat.needdrummer.features.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.databinding.FragmentStartBinding

@AndroidEntryPoint
class StartFragment : CoreFragment<FragmentStartBinding, StartViewModel>() {

    override val viewModel: StartViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_start

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isAuthenticated.observe(viewLifecycleOwner) {
            val direction = if (it) {
                StartFragmentDirections.showMainFragment()
            } else {
                StartFragmentDirections.showAuthFragment()
            }
            navigateTo(direction)
        }
   }
}