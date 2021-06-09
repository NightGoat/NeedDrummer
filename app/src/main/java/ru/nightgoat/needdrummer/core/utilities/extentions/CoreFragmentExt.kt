package ru.nightgoat.needdrummer.core.utilities.extentions

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.core.CoreViewModel
import timber.log.Timber

fun <T : ViewDataBinding, S : CoreViewModel>CoreFragment<T, S>.popBackStack() {
    navController.popBackStack()
}

fun <T : ViewDataBinding, S : CoreViewModel>CoreFragment<T, S>.navigateTo(action: Int) {
    navController.navigate(action)
}

fun <T : ViewDataBinding, S : CoreViewModel>CoreFragment<T, S>.navigateTo(direction: NavDirections) {
    try {
        navController.navigate(direction)
    } catch (e: Exception) {
        Timber.e(e, "There is no navigation direction with id = ${direction.actionId}")
        Navigation.findNavController(
            requireActivity(),
            R.id.mainHostFragment
        ).navigate(direction)
    }
}

fun <T : ViewDataBinding, S : CoreViewModel>CoreFragment<T, S>.navigateBy(navResId: Int) {
    navResId.let(navController::navigate)
}

fun <T : ViewDataBinding, S : CoreViewModel>CoreFragment<T, S>.navigatePopTo(navResId: Int?, isInclusive: Boolean) {
    navController.apply {
        navResId?.let {
            popBackStack(it, isInclusive)
        } ?: popBackStack()
    }
}

fun <T : Any> CoreFragment<*, *>.onAnyChange(data: LiveData<T>?, stateHandle: (T) -> Unit) {
    data?.observe(viewLifecycleOwner, {
        stateHandle(it)
    })
}

fun <T : Any> CoreFragment<*, *>.onAnyChange(data: Flow<T>, stateHandle: (T) -> Unit) {
    lifecycleScope.launch {
        data.buffer(1).collect {
            stateHandle(it)
        }
    }
}