package ru.nightgoat.needdrummer.data.repos

import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.utilities.extentions.flatMapIfSuccess
import ru.nightgoat.needdrummer.data.base.IBaseRepo
import ru.nightgoat.needdrummer.data.sources.local.IAuthPreference
import ru.nightgoat.needdrummer.data.sources.remote.IFirebaseRepo
import ru.nightgoat.needdrummer.models.util.Email
import ru.nightgoat.needdrummer.models.util.Name
import ru.nightgoat.needdrummer.models.util.Password
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepo @Inject constructor(
    override val localSource: IAuthPreference,
    override val remoteSource: IFirebaseRepo
) : IAuthRepo {

    override suspend fun login(email: Email, password: Password): AnyResult {
        return remoteSource.login(email, password).flatMapIfSuccess {
            localSource.saveUser(it)
        }
    }

    override suspend fun rememberPassword(email: Email): AnyResult {
        return remoteSource.resetPassword(email)
    }

    override suspend fun register(email: Email, password: Password, name: Name): AnyResult {
        return remoteSource.register(email, password).flatMapIfSuccess {
            localSource.saveName(name)
        }
    }
}

/**
 * Authonication Repo
 * @property localSource saves email
 * @property remoteSource instance of FireBase.Auth
 * */
interface IAuthRepo : IBaseRepo<IAuthPreference, IFirebaseRepo> {
    /** Checks is login valid, and saves email to local repo */
    suspend fun login(email: Email, password: Password): AnyResult

    /** Sends user link to change password */
    suspend fun rememberPassword(email: Email): AnyResult

    /** Register user in firebase, and saves user name */
    suspend fun register(email: Email, password: Password, name: Name): AnyResult
}