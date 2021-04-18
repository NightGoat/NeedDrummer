package ru.nightgoat.needdrummer.core.platform.models

import androidx.navigation.NavDirections

typealias AnyResult = SResult<Any>

sealed class SResult<out T : Any> : IResult<T> {

    override val data: T? = null
    override var isNeedHandle: Boolean = false
    override var isHandled: Boolean = false

    open class Success<out T : Any>(
        override val data: T
    ) : SResult<T>()

    object AnySuccess : Success<Any>(true)

    sealed class Loading : SResult<Nothing>() {
        override var isNeedHandle: Boolean = true
        object Show: Loading()
        object Hide: Loading()
    }

    data class Toast(val message: String) : SResult<Any>() {
        override var isNeedHandle: Boolean = true
    }

    object Empty : SResult<Nothing>()

    sealed class NavigateResult : SResult<Any>() {
        override var isNeedHandle = true

        data class NavigateTo(val direction: NavDirections) : NavigateResult()

        data class NavigateBy(
            val navigateResourceId: Int
        ) : NavigateResult()

        data class NavigatePopTo(
            val navigateResourceId: Int? = null,
            val isInclusive: Boolean = false
        ) : NavigateResult()

        object NavigateNext : NavigateResult()
        object NavigateBack : NavigateResult()
    }

    //---- Error States
    sealed class ErrorResult : SResult<Nothing>() {
        open val message: String? = null
        open val exception: Throwable? = null
        override var isNeedHandle = true

        data class Error(
            override val message: String? = null,
            val code: Int = 0,
            override val exception: Throwable? = null
        ) : ErrorResult()

        data class Alert(
            override val message: String? = null,
            val dialogTitle: Any? = null,
            override val exception: Throwable? = null,
            val okHandler: (() -> Unit)? = null,
            val okTitle: Int? = null
        ) : ErrorResult()

        object AuthError: ErrorResult()
    }

}