package ru.nightgoat.needdrummer.core

import androidx.databinding.ViewDataBinding
import com.rasalexman.sresultpresentation.databinding.BaseBindingFragment

abstract class CoreFragment<T : ViewDataBinding, S : CoreViewModel> : BaseBindingFragment<T, S>()