package ru.nightgoat.needdrummer.core.platform

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.platform.Constants.ONE_MINUTE_TIMEOUT
import ru.nightgoat.needdrummer.core.platform.navigation.BackFragmentResultHelper
import ru.nightgoat.needdrummer.core.utilities.extentions.restartApp
import ru.nightgoat.needdrummer.core.utilities.extentions.setFragmentResultCode
import javax.inject.Inject
import kotlin.system.exitProcess

class CoreNavigator @Inject constructor(
    private val context: Context,
    private val foregroundActivityProvider: ForegroundActivityProvider,
    override val backFragmentResultHelper: BackFragmentResultHelper,
) : ICoreNavigator {

    override val functionsCollector: FunctionsCollector by lazy {
        FunctionsCollector(foregroundActivityProvider.onPauseStateLiveData)
    }

    private fun getFragmentStack() = foregroundActivityProvider.getActivity()?.fragmentStack

    private fun getFragmentStackSize(): Int {
        return foregroundActivityProvider.getActivity()?.supportFragmentManager?.backStackEntryCount
            ?: 0
    }

    override fun isOneScreenInStack(): Boolean {
        return getFragmentStackSize() == 1
    }

    override fun goBackWithArgs(args: Bundle) {
        runOrPostpone {
            getFragmentStack()?.popReturnArgs(args = args)
        }
    }

    override fun goBackWithResultCode(code: Int?) {
        if (code == null) {
            backFragmentResultHelper.getFuncAndClear(null)
            goBack()
        } else {
            goBackWithArgs(
                args = Bundle().apply {
                    setFragmentResultCode(code)
                })
        }

    }

    override fun goBack() {
        runOrPostpone {
            getFragmentStack()?.pop()
        }
    }

    override fun goBackTo(fragmentName: String?) {
        runOrPostpone {
            getFragmentStack()?.pop(fragmentName)

        }
    }

    override fun finishApp(restart: Boolean) {
        runOrPostpone {
            foregroundActivityProvider.getActivity()?.finish()
            if (restart) {
                context.restartApp()
            } else {
                exitProcess(0)
            }
        }

    }

    override fun openAlertScreen(
        message: String,
        iconRes: Int,
        textColor: Int?,
        pageNumber: String?,
        timeAutoExitInMillis: Int?,
        onlyIfFirstAlert: Boolean
    ) {
        runOrPostpone {
            getFragmentStack()?.let {

                if (onlyIfFirstAlert && it.peek() is AlertFragment) {
                    return@let
                }

                val fragment = AlertFragment.create(
                    message = message,
                    iconRes = iconRes,
                    textColor = textColor,
                    pageNumber = pageNumber,
                    timeAutoExitInMillis = timeAutoExitInMillis
                )
                it.push(fragment, CustomAnimation.vertical)

            }
        }
    }

    override fun openAlertScreen(failure: Failure, pageNumber: String, timeAutoExitInMillis: Int?) {
        openAlertScreen(
            message = failureInterpreter.getFailureDescription(failure).message,
            iconRes = failureInterpreter.getFailureDescription(failure).iconRes,
            textColor = failureInterpreter.getFailureDescription(failure).textColor,
            timeAutoExitInMillis = timeAutoExitInMillis,
            pageNumber = pageNumber)
    }

    override fun openSupportScreen() {
        runOrPostpone {
            getFragmentStack()?.push(SupportFragment())
        }
    }

    override fun <Params> showProgress(useCase: UseCase<Any, Params>) {
        showProgressLoadingData()
    }

    override fun showProgress(title: String, timeoutInSec: Int?) {
        runOrPostpone {
            foregroundActivityProvider.getActivity()?.showSimpleProgress(
                title = title,
                timeoutInSec = timeoutInSec
            )
        }
    }

    override fun showProgress(title: String) {
        runOrPostpone {
            foregroundActivityProvider.getActivity()?.showSimpleProgress(
                title = title
            )
        }
    }

    override fun showProgressLoadingData(timeoutInSec: Int?) {
        runOrPostpone {
            showProgress(
                title = context.getString(R.string.data_loading),
                timeoutInSec = timeoutInSec
            )
        }
    }

    override fun showProgressLoadingData() {
        runOrPostpone {
            showProgress(
                title = context.getString(R.string.data_loading),
                timeoutInSec = Constants.ONE_MINUTE_TIMEOUT
            )
        }
    }

    override fun showProgressLoadingData(handleFailure: ((Failure) -> Unit)?, timeoutInSec: Int?) {
        runOrPostpone {
            foregroundActivityProvider.getActivity()?.showSimpleProgress(
                title = context.getString(R.string.data_loading),
                handleFailure = handleFailure,
                timeoutInSec = timeoutInSec
            )
        }
    }

    override fun showProgressLoadingData(handleFailure: ((Failure) -> Unit)?, timeoutInSec: Int?, title: String) {
        runOrPostpone {
            foregroundActivityProvider.getActivity()?.showSimpleProgress(
                title = title,
                handleFailure = handleFailure,
                timeoutInSec = timeoutInSec
            )
        }
    }

    override fun showProgressLoadingData(handleFailure: ((Failure) -> Unit)?) {
        runOrPostpone {
            foregroundActivityProvider.getActivity()?.showSimpleProgress(
                title = context.getString(R.string.data_loading),
                handleFailure = handleFailure,
                timeoutInSec = ONE_MINUTE_TIMEOUT
            )
        }
    }

    override fun showProgressInfinite() {
        runOrPostpone {
            showProgress(
                title = context.getString(R.string.data_loading),
                timeoutInSec = null
            )
        }
    }

    override fun showProgressConnection() {
        runOrPostpone {
            showProgress(context.getString(R.string.connection_setup))
        }
    }

    override fun hideProgress() {
        runOrPostpone {
            foregroundActivityProvider.getActivity()?.hideProgress()
        }
    }
}

fun ICoreNavigator.runOrPostpone(function: () -> Unit) {
    functionsCollector.executeFunction(function)
}

interface ICoreNavigator {
    val functionsCollector: FunctionsCollector
    val backFragmentResultHelper: BackFragmentResultHelper
    fun isOneScreenInStack(): Boolean
    fun goBackWithArgs(args: Bundle)
    fun goBackWithResultCode(code: Int?)
    fun goBack()
    fun goBackTo(fragmentName: String?)

    fun finishApp(restart: Boolean = false)
    fun openAlertScreen(
        message: String,
        iconRes: Int = 0,
        textColor: Int? = null,
        pageNumber: String? = null,
        timeAutoExitInMillis: Int? = null,
        onlyIfFirstAlert: Boolean = false
    )

    fun openAlertScreen(failure: Failure, pageNumber: String = "96", timeAutoExitInMillis: Int? = null)
    fun <Params> showProgress(useCase: UseCase<Any, Params>)
    fun showProgress(title: String, timeoutInSec: Int? = ONE_MINUTE_TIMEOUT)
    fun showProgress(title: String)
    fun showProgressLoadingData(timeoutInSec: Int? = ONE_MINUTE_TIMEOUT)
    fun showProgressLoadingData()
    fun showProgressInfinite()
    fun showProgressLoadingData(handleFailure: ((Failure) -> Unit)? = null, timeoutInSec: Int? = Constants.ONE_MINUTE_TIMEOUT)
    fun showProgressLoadingData(handleFailure: ((Failure) -> Unit)? = null)
    fun showProgressLoadingData(handleFailure: ((Failure) -> Unit)? = null, timeoutInSec: Int? = Constants.ONE_MINUTE_TIMEOUT, title: String)
    fun hideProgress()
}

inline fun <reified T : CoreFragment<*, *>> ICoreNavigator.backTo() {
    this.goBackTo(T::class.java.simpleName)
}

class FunctionsCollector(private val needCollectLiveData: LiveData<Boolean>) {

    private val functions: MutableList<() -> Unit> = mutableListOf()

    init {
        Handler(Looper.getMainLooper()).post {
            needCollectLiveData.observeForever { needCollect ->
                if (!needCollect) {
                    functions.map { it }.forEach {
                        it()
                        functions.remove(it)
                    }
                }
            }
        }
    }

    fun executeFunction(func: () -> Unit) {
        if (needCollectLiveData.value == true) {
            functions.add(func)
        } else {
            func()
        }
    }
}