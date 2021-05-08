package ru.nightgoat.needdrummer.repos

import android.content.Context
import androidx.annotation.StringRes
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.utilities.extentions.unsafeLazy
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import javax.inject.Inject

class ResourcesRepo @Inject constructor(
    private val context: Context
) : IResourcesRepo {
    override val authError by lazy { context.getString(R.string.auth_error) }
    override val registerError: String by lazyString(R.string.register_error)
    override val rememberPassError: String by lazyString(R.string.remember_pass_error)
    override val wrongEmailOrPass by lazy { context.getString(R.string.wrong_email_or_pass) }

    private fun lazyString(@StringRes stringId: Int) = unsafeLazy {
        context.getString(stringId)
    }
}