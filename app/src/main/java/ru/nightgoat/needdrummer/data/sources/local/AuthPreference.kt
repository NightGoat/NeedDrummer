package ru.nightgoat.needdrummer.data.sources.local

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import com.rasalexman.sresult.common.extensions.anySuccess
import com.rasalexman.sresult.common.typealiases.AnyResult
import com.rasalexman.sresult.data.base.ILocalDataSource
import ru.nightgoat.needdrummer.models.repo.UserModel
import ru.nightgoat.needdrummer.models.util.Name
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreference @Inject constructor(context: Context): IAuthPreference, KotprefModel(context = context) {

    private var email: String by stringPref()
    private var name: String by stringPref()

    override fun isAuthenticated(): Boolean {
        return email.isNotEmpty()
    }

    override fun saveUser(user: UserModel): AnyResult {
        email = user.email.value
        return anySuccess()
    }

    override fun saveName(name: Name): AnyResult {
        this.name = name.value
        return anySuccess()
    }

    override fun clearAuth(): AnyResult {
        email = ""
        return anySuccess()
    }
}

interface IAuthPreference : ILocalDataSource {
    fun isAuthenticated(): Boolean
    fun saveUser(user: UserModel): AnyResult
    fun saveName(name: Name): AnyResult
    fun clearAuth(): AnyResult
}