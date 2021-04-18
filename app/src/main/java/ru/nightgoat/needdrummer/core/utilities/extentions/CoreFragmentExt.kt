package ru.nightgoat.needdrummer.core.utilities.extentions

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
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