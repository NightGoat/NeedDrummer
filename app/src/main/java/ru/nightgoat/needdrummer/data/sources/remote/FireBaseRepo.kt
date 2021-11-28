package ru.nightgoat.needdrummer.data.sources.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rasalexman.sresult.common.extensions.orError
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.base.IRemoteDataSource
import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.nightgoat.needdrummer.models.repo.UserModel
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.Password
import ru.nightgoat.needdrummer.models.util.toEmail
import ru.nightgoat.needdrummer.providers.IStringResources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireBaseRepo @Inject constructor(
    private val stringResources: IStringResources,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IFirebaseRepo {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override suspend fun login(email: Email, password: Password): SResult<UserModel> {
        return withContext(defaultDispatcher) {
            val result = auth.signInWithEmailAndPassword(email.value, password.value).await()
            result.user.toUserModel(stringResources.wrongEmailOrPass)
        }
    }

    override suspend fun register(email: Email, password: Password): SResult<UserModel> {
        return withContext(defaultDispatcher) {
            val result = auth.createUserWithEmailAndPassword(email.value, password.value).await()
            result.user.toUserModel(stringResources.wrongEmailOrPass)
        }
    }

    override suspend fun resetPassword(email: Email): SResult<Boolean> {
        return withContext(defaultDispatcher) {
            auth.sendPasswordResetEmail(email.value).isSuccessful.toSuccessResult()
        }
    }

    private fun FirebaseUser?.toUserModel(errorMessage: String = "empty email!"): SResult<UserModel> {
        return this?.email?.run {
            UserModel(this.toEmail()).toSuccessResult()
        }.orError(errorMessage)
    }
}

interface IFirebaseRepo : IRemoteDataSource {
    suspend fun login(email: Email, password: Password): SResult<UserModel>
    suspend fun register(email: Email, password: Password): SResult<UserModel>
    suspend fun resetPassword(email: Email): SResult<Boolean>
}