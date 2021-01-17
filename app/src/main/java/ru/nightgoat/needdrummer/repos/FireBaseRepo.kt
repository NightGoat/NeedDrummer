package ru.nightgoat.needdrummer.repos

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import ru.nightgoat.needdrummer.core.platform.Either
import ru.nightgoat.needdrummer.core.platform.Failure
import ru.nightgoat.needdrummer.core.platform.orLeft
import ru.nightgoat.needdrummer.models.User
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo

class FireBaseRepo() : IFirebaseRepo {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Either<Failure, User> {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?.let { firebaseUser ->
            firebaseUser.email?.let { email ->
                val user = User(email)
                Either.Right(user)
            }
        }.orLeft(Failure.AuthError)
    }

    override suspend fun register(email: String, password: String): Either<Failure, User> {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.let { firebaseUser ->
            firebaseUser.email?.let { email ->
                val user = User(email)
                Either.Right(user)
            }
        }.orLeft(Failure.AuthError)
    }
}