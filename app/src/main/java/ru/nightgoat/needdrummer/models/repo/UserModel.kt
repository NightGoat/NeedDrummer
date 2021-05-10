package ru.nightgoat.needdrummer.models.repo

import ru.nightgoat.needdrummer.core.platform.models.IConvertableTo
import ru.nightgoat.needdrummer.models.domain.UserData
import ru.nightgoat.needdrummer.models.util.Email

data class UserModel(
    val email: Email,
) : IConvertableTo<UserData> {
    override fun convertTo(): UserData? {
        return UserData(email)
    }
}