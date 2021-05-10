package ru.nightgoat.needdrummer.domain

import kotlinx.coroutines.flow.Flow
import ru.nightgoat.needdrummer.core.platform.models.SResult

interface IUseCase {
    interface InOut<T, V> {
        suspend fun invoke(param: T): V
    }

    interface In<T> {
        suspend fun invoke(param: T)
    }

    interface Out<T> {
        suspend fun invoke(): T
    }

    interface Invokable {
        suspend fun invoke()
    }

    interface FlowInOut<T, V> {
        fun invoke(param: T): Flow<V>
    }

    interface FlowOut<T> {
        fun invoke(): Flow<T>
    }

    interface InOutSResult<T, R : Any> {
        suspend fun invoke(param: T): SResult<R>
    }
}