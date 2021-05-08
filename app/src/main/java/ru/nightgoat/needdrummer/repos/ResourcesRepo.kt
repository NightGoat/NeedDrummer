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
    override val authError by lazyString(R.string.auth_error)
    override val registerError by lazyString(R.string.register_error)
    override val rememberPassError by lazyString(R.string.remember_pass_error)
    override val wrongEmailOrPass by lazyString(R.string.wrong_email_or_pass )

    private fun lazyString(@StringRes stringId: Int) = unsafeLazy {
        context.getString(stringId)
    }
}