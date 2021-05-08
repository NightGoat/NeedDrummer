package ru.nightgoat.needdrummer.providers

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import javax.inject.Inject

class AuthPreference @Inject constructor(context: Context): IAuthPreference, KotprefModel(context = context){

    override fun isAuthenticated(): Boolean {
        return email.isNotEmpty()
    }

    override var email: String by stringPref()
    override var password: String by stringPref()

}

interface IAuthPreference {
    fun isAuthenticated(): Boolean

    var email: String
    var password: String
}