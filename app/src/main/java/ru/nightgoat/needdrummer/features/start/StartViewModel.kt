package ru.nightgoat.needdrummer.features.start

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import ru.nightgoat.needdrummer.core.CoreViewModel
import ru.nightgoat.needdrummer.providers.IAuthPreference
import javax.inject.Inject


class StartViewModel : CoreViewModel(){

    @Inject
    lateinit var authPreference: IAuthPreference

    init {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun getStartPageNavigation() {
//        return if(userSettings.usePinCode && !authPreference.isNotAuthenticated()) {
//            navigateToResult(NavigationMainDirections.showPinCodeFragment(StartViewModel::class.simpleName.orEmpty()))
//        } else {
//            navigateToResult(NavigationMainDirections.showAuthFragment())
//        }
    }

    companion object {
        private const val START_TIMEOUT_MS = 2000L
    }
}