/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.nightgoat.needdrummer.core.platform

import ru.nightgoat.needdrummer.core.platform.Either.Left
import ru.nightgoat.needdrummer.core.platform.Either.Right

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(fnL: ((L) -> Any)? = null, fnR: ((R) -> Any)? = null): Any =
        when (this) {
            is Left -> fnL?.invoke(a) ?: Unit
            is Right -> fnR?.invoke(b) ?: Unit
        }
}

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

suspend fun <A, B, C> (suspend (A) -> B).csSuspend(f: suspend (B) -> C): suspend (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> = when (this) {
    is Left -> Left(a)
    is Right -> fn(b)
}

suspend fun <T, L, R> Either<L, R>.flatMapSuspend(fn: suspend (R) -> Either<L, T>): Either<L, T> = when (this) {
    is Left -> Left(a)
    is Right -> fn(b)
}

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))

suspend fun <T, L, R> Either<L, R>.mapSuspend(fn: suspend (R) -> (T)): Either<L, T> = this.flatMapSuspend(fn.csSuspend(::right))

fun <L, R> Either<L, R>.rightToLeft(fnRtoL: (R) -> (L?)): Either<L, R> = when (this) {
    is Left -> Left(a)
    is Right -> {
        fnRtoL(b).let { result ->
            when (result) {
                null -> Right(b)
                else -> Left(result)
            }
        }
    }
}

fun <T> T.toRight(): Either<Failure, T> = Right(this)

fun <T : Failure> T.toLeft(): Either<Failure, Nothing> = Left(this)

suspend fun <T : Any, R : Any> Either<Failure, T>.combineWith(usecase: UseCase<R, T>): Either<Failure, R> {
    return if (this is Right<T>) {
        val data = this.b
        usecase.invoke(data)
    } else {
        this as Left<Failure>
    }
}

suspend fun <T : Any, R : Any> Either<Failure, T>.combineFailure(params: T, usecase: UseCase<R, T>): Either<Failure, R> {
    return if (this is Left<Failure>) {
        usecase.invoke(params)
    } else {
        this as Left<Failure>
    }
}

fun <T, R> Either<T, R>.getRight(): R? {
    return if (this is Right<R>) this.b else null
}

fun <T, R> Either<T, R>.getLeft(): T? {
    return if (this is Left<T>) this.a else null
}

fun <T, R> Either<T, R>?.orLeft(failure: T): Either<T, R> {
    return this ?: Left(failure)
}

fun <R: Any> Either<Failure, R>?.orLeftWithFailure(text: String? = null, failure: Failure = Failure.MessageFailure(text.orEmpty())): Either<Failure, R> {
    return this.orLeft(failure)
}

