package ru.nightgoat.needdrummer.core.utilities.extentions

import androidx.navigation.NavDirections
import ru.nightgoat.kextensions.takeIfNotEmpty
import ru.nightgoat.needdrummer.core.platform.models.IConvertable
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.core.platform.models.convert
import ru.nightgoat.needdrummer.core.utilities.*
import ru.nightgoat.needdrummer.models.states.ErrorType

// /------ ViewResult extensions
inline fun <reified T : Any> Any.successResult(data: T): SResult<T> = SResult.Success(data)

fun Any?.emptyResult() = SResult.Empty

fun Any.anySuccess() = SResult.AnySuccess

fun Any.navigateToResult(
    to: NavDirections
) = SResult.NavigateResult.NavigateTo(to)

fun Any.navigateBy(
    navigateResourceId: Int
) = SResult.NavigateResult.NavigateBy(navigateResourceId)

fun Any.navigatePopTo(
    navigateResourceId: Int? = null,
    isInclusive: Boolean = false
) = SResult.NavigateResult.NavigatePopTo(navigateResourceId, isInclusive)

fun Any.navigateBackResult() =
    SResult.NavigateResult.NavigateBack

fun Any.navigateNextResult() =
    SResult.NavigateResult.NavigateNext

fun Any.errorResult(
    message: String = "",
    code: Int = -1,
    exception: Throwable? = null
) = SResult.ErrorResult.Error(message, code, exception)

fun Any.alertResult(
    message: String = "",
    dialogTitle: Any? = null,
    exception: Throwable? = null,
    okHandler: UnitHandler? = null,
    okTitle: Int? = null
) = SResult.ErrorResult.Alert(message, dialogTitle, exception, okHandler, okTitle)

fun Any.toastResult(message: String) = SResult.Toast(message)

// /-------- toState Convertables
inline fun <reified T : Any> T?.toSuccessResult(orDefault: SResult<T> = emptyResult()): SResult<T> =
    this?.let {
        successResult(it)
    } ?: orDefault

inline fun <reified T : Throwable> T.toErrorResult() =
    errorResult(this.message ?: this.cause?.message.orEmpty(), 0, this)

inline fun <reified T : Throwable> T.toAlertResult(
    okTitle: Int? = null,
    noinline okHandler: UnitHandler? = null
) = alertResult(
    message = this.message ?: this.cause?.message.orEmpty(),
    exception = this,
    okHandler = okHandler,
    okTitle = okTitle
)

inline fun <reified T : NavDirections> T.toNavigateResult() = navigateToResult(this)

// /-------- HANDLE FUNCTION
fun SResult<*>.handle() {
    if (isNeedHandle) isHandled = true
}

// /------- Mapper function
@Suppress("UNCHECKED_CAST")
inline fun <reified O : Any> ResultList<IConvertable>.mapListTo(): ResultList<O> {
    return when (this) {
        is SResult.Success -> {
            data.mapNotNull { it.convert<O>() }.mapToResult()
        }
        else -> this as ResultList<O>
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified O : Any> ResultList<O>.getList(): List<O> {
    return when (this) {
        is SResult.Success -> data
        else -> emptyList()
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified O : Any, reified I : IConvertable> SResult<I>.convertTo(): SResult<O> {
    return when (this) {
        is SResult.Success -> {
            this.data.convert<O>()?.toSuccessResult() ?: emptyResult()
        }
        else -> this as SResult<O>
    }
}

fun SResult.ErrorResult.getMessage(): Any? {
    return (this.message.takeIfNotEmpty()
        ?: this.exception?.message
        ?: this.exception?.cause?.message)
}

///--- Inline Applying functions
inline fun <reified I : Any> SResult<I>.doIfSuccess(block: UnitHandler): SResult<I> {
    if (this is SResult.Success) block()
    return this
}

///--- Inline Applying functions
inline fun <reified I : Any> SResult<I>.doIfError(block: UnitHandler): SResult<I> {
    if (this is SResult.ErrorResult) block()
    return this
}

suspend inline fun <reified I : Any> SResult<I>.doIfSuccessSuspend(crossinline block: suspend () -> Unit): SResult<I> {
    if (this is SResult.Success) block()
    return this
}

// /--- Inline Applying functions
inline fun <reified I : Any> SResult<I>.applyIfSuccess(block: InHandler<I>): SResult<I> {
    if (this is SResult.Success) block(this.data)
    return this
}

// /--- Inline Applying functions
inline fun <reified I> SResult<Any>.applyIfSuccessTyped(block: InHandler<I>): SResult<Any> {
    if (this is SResult.Success && this.data is I) block(this.data as I)
    return this
}

inline fun <reified I : Any> SResult<I>.applyIfError(block: InHandler<SResult.ErrorResult>): SResult<I> {
    if (this is SResult.ErrorResult) block(this)
    return this
}

inline fun <reified I : SResult<*>> SResult<*>.applyIfType(block: I.() -> Unit): SResult<*> {
    if (this::class == I::class) block(this as I)
    return this
}

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <reified I : SResult<*>> SResult<*>.applyIfTypeSuspend(crossinline block: suspend I.() -> Unit): SResult<*> {
    if (this::class == I::class) block(this as I)
    return this
}

@Suppress("UNCHECKED_CAST")
inline fun <reified I : Any, reified O : Any> SResult<I>.mapIfSuccess(block: I.() -> SResult<O>): SResult<O> {
    return if (this is SResult.Success) block(this.data)
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
inline fun <reified I : Any, reified O : Any> SResult<I>.flatMapIfSuccess(block: (I) -> SResult<O>): SResult<O> {
    return if (this is SResult.Success) block(this.data)
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
inline fun <reified I : Any, reified O : Any> SResult<I>.flatMapIfError(block: () -> SResult<O>): SResult<O> {
    return if (this is SResult.ErrorResult) block()
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
inline fun <reified I : Any, reified O : Any> SResult<I>.flatMapIfSuccessToNavResult(block: (I) -> NavDirections): SResult<O> {
    return if (this is SResult.Success) block(this.data).toNavigateResult() as SResult<O>
    else this as SResult<O>
}

inline fun <reified T : List<*>> T.mapToResult(): SResult<T> {
    return this.takeIf { it.isNotEmpty() }?.toSuccessResult() ?: emptyResult()
}

inline fun <reified T : Any> T?.mapToResult(): SResult<T> {
    return this.toSuccessResult()
}


@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <reified I : Any> SResult<I>.applyIfSuccessSuspend(crossinline block: SInHandler<I>): SResult<I> {
    if (this is SResult.Success) block(this.data)
    return this
}

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <reified I : Any> SResult<I>.applyIfEmptySuspend(crossinline block: SUnitHandler): SResult<I> {
    if (this is SResult.Empty) block()
    return this
}

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <reified I : Any> SResult<I>.applyIfErrorSuspend(crossinline block: SInHandler<SResult.ErrorResult>): SResult<I> {
    if (this is SResult.ErrorResult) block(this)
    return this
}

@Suppress("UNCHECKED_CAST")
inline fun <reified I : Any> SResult<*>.mapIfSuccessTyped(block: I.() -> SResult<*>): SResult<*> {
    return if (this is SResult.Success && this.data is I) block(this.data as I)
    else this
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified I : Any, reified O : Any> SResult<I>.mapIfSuccessSuspend(crossinline block: suspend I.() -> SResult<O>): SResult<O> {
    return if (this is SResult.Success) block(this.data)
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified I : Any, reified O : Any> SResult<I>.mapIfErrorSuspend(crossinline block: suspend () -> SResult<O>): SResult<O> {
    return if (this is SResult.ErrorResult) block()
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified I : Any, reified O : Any> SResult<I>.mapIfEmptySuspend(crossinline block: suspend () -> SResult<O>): SResult<O> {
    return if (this is SResult.Empty) block()
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified I : Any, reified O : Any> SResult<I>.flatMapIfSuccessSuspend(crossinline block: suspend (I) -> SResult<O>): SResult<O> {
    return if (this is SResult.Success) block(this.data)
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified I : Any, reified O : Any> SResult<I>.flatMapIfErrorSuspend(crossinline block: suspend () -> SResult<O>): SResult<O> {
    return if (this is SResult.ErrorResult) block()
    else this as SResult<O>
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified I : Any, reified O : Any> SResult<I>.flatMapIfEmptySuspend(crossinline block: suspend () -> SResult<O>): SResult<O> {
    return if (this is SResult.Empty) block()
    else this as SResult<O>
}

inline fun <reified I : SResult<*>> SResult<*>.mapIfType(block: I.() -> SResult<*>): SResult<*> {
    return if (this::class == I::class) block(this as I)
    else this
}

suspend inline fun <reified I : SResult<*>> SResult<*>.mapIfTypeSuspend(crossinline block: suspend I.() -> SResult<*>): SResult<*> {
    return if (this::class == I::class) block(this as I)
    else this
}

val <T : Any> SResult<T>.isSuccess: Boolean
    get() = this is SResult.Success<T>

val <T : Any> SResult<T>.isError: Boolean
    get() = this is SResult.ErrorResult

fun <T : Any> SResult<T>?.orError(
    message: String = "",
    code: Int = 0,
    exception: Throwable? = null,
    type: ErrorType = ErrorType.ORDINARY
): SResult<T> = this ?: SResult.ErrorResult.Error(message, code, exception, type)