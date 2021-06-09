package ru.nightgoat.needdrummer.features.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.core.utilities.extentions.navigateTo
import ru.nightgoat.needdrummer.databinding.FragmentStartBinding

@AndroidEntryPoint
class StartFragment : CoreFragment<FragmentStartBinding, StartViewModel>() {

   override val vm: StartViewModel by viewModels()
   override fun getLayoutId(): Int = R.layout.fragment_start

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      vm.isAuthenticated.observe(viewLifecycleOwner) {
         val direction = if(it) {
            StartFragmentDirections.showMainFragment()
         } else {
            StartFragmentDirections.showAuthFragment()
         }
         navigateTo(direction)
      }
   }
}