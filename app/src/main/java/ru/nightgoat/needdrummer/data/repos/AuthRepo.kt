package ru.nightgoat.needdrummer.data.repos

import com.rasalexman.sresult.common.extensions.flatMapIfSuccess
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.data.base.IBaseRepository
import ru.nightgoat.needdrummer.data.sources.local.IAuthPreference
import ru.nightgoat.needdrummer.data.sources.remote.IFirebaseRepo
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.Name
import ru.nightgoat.needdrummer.models.util.Password
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepo @Inject constructor(
    override val localDataSource: IAuthPreference,
    override val remoteDataSource: IFirebaseRepo
) : IAuthRepo {

    override suspend fun login(email: Email, password: Password): AnyResult {
        return remoteDataSource.login(email, password).flatMapIfSuccess {
            localDataSource.saveUser(it)
        }
    }

    override suspend fun rememberPassword(email: Email): AnyResult {
        return remoteDataSource.resetPassword(email)
    }

    override suspend fun register(email: Email, password: Password, name: Name): AnyResult {
        return remoteDataSource.register(email, password).flatMapIfSuccess {
            localDataSource.saveName(name)
        }
    }
}

/**
 * Authonication Repo
 * @property localSource saves email
 * @property remoteSource instance of FireBase.Auth
 * */
interface IAuthRepo : IBaseRepository<IAuthPreference, IFirebaseRepo> {
    /** Checks is login valid, and saves email to local repo */
    suspend fun login(email: Email, password: Password): AnyResult

    /** Sends user link to change password */
    suspend fun rememberPassword(email: Email): AnyResult

    /** Register user in firebase, and saves user name */
    suspend fun register(email: Email, password: Password, name: Name): AnyResult
}