package ru.nightgoat.needdrummer.core.utilities.extentions

fun <T> Any?.implementationOf(clazz: Class<T>): T? {
    return if (this != null && clazz.isInstance(this)) {
        @Suppress("UNCHECKED_CAST")
        this as T
    } else {
        return null
    }
}

inline fun <reified T, reified R> R.unsafeLazy(noinline init: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, init)