package ru.nightgoat.needdrummer.core.utilities

import android.util.Base64
import com.lenta.shared.utilities.Logg

fun getBaseAuth(login: String?, password: String?) : String {
    return "Basic " + "$login:$password".encode().dropLast(1)
}

fun String.encode(): String {
    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
}

/**
 * Helper nullability function
 */
inline fun <reified T> T?.orIfNull(input: () -> T): T {
    return this ?: input()
}

fun Any?.logIfNull(message: String): Any {
    return this ?: Logg.e { message }
}

/**
 * Из строки получает Enum
 * */
inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? =
    T::class.java.enumConstants?.firstOrNull { it.name == name }

inline fun <reified T : Enum<*>> enumValueOrDefault(name: String, default: T): T =
    T::class.java.enumConstants?.firstOrNull { it.name == name } ?: default
