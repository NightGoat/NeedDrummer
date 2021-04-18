package ru.nightgoat.needdrummer.repos.Interfaces

import ru.nightgoat.needdrummer.core.platform.models.AnyResult

/**
 * child:
 * @see ru.nightgoat.needdrummer.repos.FireBaseRepo
 * */
interface IFirebaseRepo {
    suspend fun login(email: String, password: String): AnyResult
    suspend fun register(email: String, password: String): AnyResult
    suspend fun resetPassword(email: String): AnyResult
}