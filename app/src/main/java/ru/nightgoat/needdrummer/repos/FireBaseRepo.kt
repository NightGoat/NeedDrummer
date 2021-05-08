package ru.nightgoat.needdrummer.repos

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import pro.krit.core.common.extensions.orError
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.models.User
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireBaseRepo @Inject constructor(
    private val stringResources: IResourcesRepo
) : IFirebaseRepo {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override suspend fun login(email: String, password: String): AnyResult {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?.let { firebaseUser ->
            firebaseUser.email?.let { email ->
                val user = User(email)
                SResult.Success(user)
            }.orError(
                message = stringResources.wrongEmailOrPass
            )
        }.orError(
            message = stringResources.wrongEmailOrPass
        )
    }

    override suspend fun register(email: String, password: String): AnyResult {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.let { firebaseUser ->
            firebaseUser.email?.let { email ->
                val user = User(email)
                SResult.Success(user)
            }.orError(
                message = stringResources.wrongEmailOrPass
            )
        }.orError(
            message = stringResources.wrongEmailOrPass
        )
    }

    override suspend fun resetPassword(email: String): AnyResult {
        auth.sendPasswordResetEmail(email).await()
        return SResult.AnySuccess
    }
}