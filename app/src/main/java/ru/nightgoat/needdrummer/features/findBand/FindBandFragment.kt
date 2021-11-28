package ru.nightgoat.needdrummer.features.findBand

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.databinding.FragmentFindBandBinding

@AndroidEntryPoint
class FindBandFragment: CoreFragment<FragmentFindBandBinding, FindBandViewModel>() {
    override val viewModel: FindBandViewModel by viewModels()
    override val layoutId = R.layout.fragment_find_band
}