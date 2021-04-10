package ru.nightgoat.needdrummer.core.platform.models

import androidx.navigation.NavDirections

sealed class NavigationResult {
    data class NavigateTo(val direction: NavDirections): NavigationResult()

    data class NavigateBy(
        val navigateResourceId: Int
    ) : NavigationResult()

    data class NavigatePopTo(
        val navigateResourceId: Int? = null,
        val isInclusive: Boolean = false
    ) : NavigationResult()

    object NavigateNext : NavigationResult()
    object NavigateBack : NavigationResult()
}