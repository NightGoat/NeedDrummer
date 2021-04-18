package ru.nightgoat.needdrummer.core.platform.models

import kotlin.reflect.KClass

interface IConvertable {
    fun <I : Any> convertAs(clazz: KClass<I>): I?
}

interface IConvertableTo<out T> : IConvertable {
    fun convertTo(): T?

    @Suppress("UNCHECKED_CAST")
    override fun <I : Any> convertAs(clazz: KClass<I>): I? = convertTo() as? I
}

inline fun <reified To : Any> IConvertable.convert(): To? {
    return convertAs(To::class)
}