package ru.nightgoat.needdrummer.repos.Interfaces

import ru.nightgoat.needdrummer.core.platform.Either
import ru.nightgoat.needdrummer.core.platform.Failure
import ru.nightgoat.needdrummer.models.User

/**
 * child:
 * @see ru.nightgoat.needdrummer.repos.FireBaseRepo
 * */
interface IFirebaseRepo {
    suspend fun login(email: String, password: String): Either<Failure, User>
}