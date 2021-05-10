package ru.nightgoat.needdrummer.data.sources.local

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import ru.nightgoat.needdrummer.core.platform.models.AnyResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
import ru.nightgoat.needdrummer.data.base.ILocalSource
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
        return SResult.AnySuccess
    }

    override fun saveName(name: Name): AnyResult {
        this.name = name.value
        return SResult.AnySuccess
    }

    override fun clearAuth(): AnyResult {
        email = ""
        return SResult.AnySuccess
    }
}

interface IAuthPreference : ILocalSource {
    fun isAuthenticated(): Boolean
    fun saveUser(user: UserModel): AnyResult
    fun saveName(name: Name): AnyResult
    fun clearAuth(): AnyResult
}