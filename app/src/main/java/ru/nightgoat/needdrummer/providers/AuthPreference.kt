package ru.nightgoat.needdrummer.providers

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import javax.inject.Inject

class AuthPreference @Inject constructor(context: Context): IAuthPreference, KotprefModel(context = context){

    override fun isNotAuthenticated(): Boolean {
        return email.isEmpty()
    }

    override var email: String by stringPref()
    override var password: String by stringPref()

}

interface IAuthPreference {
    fun isNotAuthenticated(): Boolean

    var email: String
    var password: String
}