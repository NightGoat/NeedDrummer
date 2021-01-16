package ru.nightgoat.needdrummer.features.findBand

import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.core.utilities.extentions.provideViewModel
import ru.nightgoat.needdrummer.databinding.FragmentFindBandBinding

class FindBandFragment: CoreFragment<FragmentFindBandBinding, FindBandViewModel>() {
    override fun getLayoutId() = R.layout.fragment_find_band

    override fun getViewModel(): FindBandViewModel {
        return provideViewModel(FindBandViewModel::class.java).also {

        }
    }
}