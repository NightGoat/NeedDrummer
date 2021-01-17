package ru.nightgoat.needdrummer.features.findBand

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.databinding.FragmentFindBandBinding

@AndroidEntryPoint
class FindBandFragment: CoreFragment<FragmentFindBandBinding, FindBandViewModel>() {
    override val vm: FindBandViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_find_band

}