package ru.nightgoat.needdrummer.repos

import android.content.Context
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

class ResourcesRepo @Inject constructor(
    context: Context
) : IResourcesRepo {
    override val authError by lazy { context.getString(R.string.auth_error) }
    override val wrongAuth by lazy { context.getString(R.string.wrong_auth) }
}