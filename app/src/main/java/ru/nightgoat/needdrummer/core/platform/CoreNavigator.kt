package ru.nightgoat.needdrummer.core.platform

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.core.platform.navigation.BackFragmentResultHelper
import ru.nightgoat.needdrummer.core.utilities.extentions.setFragmentResultCode
import ru.nightgoat.needdrummer.features.account.login.LoginFragment
import javax.inject.Inject

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

    override fun openLoginScreen() {
        runOrPostpone {
            getFragmentStack()?.push(LoginFragment())
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
    fun openLoginScreen()
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