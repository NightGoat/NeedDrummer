package ru.nightgoat.needdrummer.repos

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.models.User
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo

class FireBaseRepo : IFirebaseRepo {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): AnyResult {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?.let { firebaseUser ->
            firebaseUser.email?.let { email ->
                val user = User(email)
                SResult.Success(user)
            } ?: SResult.ErrorResult.AuthError
        } ?: SResult.ErrorResult.AuthError
    }

    override suspend fun register(email: String, password: String): AnyResult {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.let { firebaseUser ->
            firebaseUser.email?.let { email ->
                val user = User(email)
                SResult.Success(user)
            } ?: SResult.ErrorResult.AuthError
        } ?: SResult.ErrorResult.AuthError
    }

    override suspend fun resetPassword(email: String): AnyResult {
        auth.sendPasswordResetEmail(email).await()
        return SResult.AnySuccess
    }
}