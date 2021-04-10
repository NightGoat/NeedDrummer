package ru.nightgoat.needdrummer.core

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.nightgoat.needdrummer.BR
import ru.nightgoat.needdrummer.core.platform.failure.FailureInterpreter
import ru.nightgoat.needdrummer.core.platform.models.Failure
import ru.nightgoat.needdrummer.core.platform.models.NavigationResult
import ru.nightgoat.needdrummer.core.utilities.databinding.DataBindingAdapter
import ru.nightgoat.needdrummer.core.utilities.databinding.DataBindingRecyclerViewConfig
import ru.nightgoat.needdrummer.core.utilities.databinding.RecyclerViewKeyHandler
import ru.nightgoat.needdrummer.core.utilities.extentions.*

abstract class CoreFragment<T : ViewDataBinding, S : CoreViewModel> : Fragment() {

    var binding: T? = null

    abstract val vm: S

    private val keyHandlers = mutableMapOf<Int, RecyclerViewKeyHandler<*>>()
    open val canPressBack = true

    val navController: NavController
        get() = this.findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        observeFailure()

    }

    private fun observeFailure() {
        onAnyChange(vm.failure, ::showFailureHandler)
    }

    private fun showFailureHandler(failure: Failure) {
        val interpreter = FailureInterpreter(requireContext())
        val message = interpreter.getFailureDescription(failure)
        showShortToast(message)
    }

    private fun observeNavigation() {
        onAnyChange(vm.navigationLiveData, ::handleNavigation)
    }

    private fun handleNavigation(result: NavigationResult) {
        when (result) {
            is NavigationResult.NavigateTo -> {
                //hideLoading()
                val direction = result.direction
                navigateTo(direction)
            }
            is NavigationResult.NavigateBy ->navigateBy(result.navigateResourceId)
            is NavigationResult.NavigatePopTo -> navigatePopTo(result.navigateResourceId, result.isInclusive)

            NavigationResult.NavigateBack -> onBackPressed()
            NavigationResult.NavigateNext -> onNextPressed()
        }
    }

    fun showLoading() {
        showLayoutLoading()
    }

    protected fun showLayoutLoading() {
        hideKeyboard()
        //errorViewLayout?.hide() TODO
        //contentViewLayout?.hide() TODO
        //loadingViewLayout?.show() TODO
    }

    open fun onBackPressed(): Boolean {
        return if (!canPressBack) true else navController.popBackStack()
    }

    open fun onNextPressed() = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding?.run {
            setVariable(BR.vm, vm)
            lifecycleOwner = viewLifecycleOwner
            root
        } ?: throw NullPointerException("DataBinding is null")
    }

    protected open fun <Item : Any, T : ViewDataBinding> initRecycleAdapterDataBinding(
        @LayoutRes layoutId: Int,
        itemId: Int,
        onItemCreate: ((T) -> Unit)? = null,
        onItemBind: ((T, Int) -> Unit)? = null,
        onItemBindKeyHandler: ((T, Int, keyHandler: RecyclerViewKeyHandler<*>?) -> Unit)? = ::onItemBindKeyHandlerHandler,
        onItemClick: ((Int, keyHandler: RecyclerViewKeyHandler<*>?) -> Unit)? = ::onItemClickHandler,
        keyHandlerId: Int = 0,
        recyclerView: RecyclerView,
        items: LiveData<List<Item>>,
        onClickHandler: ((Int) -> Unit)? = null
    ): DataBindingRecyclerViewConfig<T> {
        val keyHandler = initRecyclerViewKeyHandler(
            tabPosition = keyHandlerId,
            recyclerView = recyclerView,
            items = items,
            onClickHandler = onClickHandler
        )

        return DataBindingRecyclerViewConfig(
            layoutId = layoutId,
            itemId = itemId,
            realisation = object : DataBindingAdapter<T> {
                override fun onCreate(binding: T) {
                    onItemCreate?.invoke(binding)
                }

                override fun onBind(binding: T, position: Int) {
                    onItemBind?.invoke(binding, position)
                    onItemBindKeyHandler?.invoke(binding, position, keyHandler)
                }
            },
            onItemClickListener = { _, _, position, _ ->
                onItemClick?.invoke(position, keyHandler)
            }
        )
    }

    protected open fun <T : ViewDataBinding> initRecycleAdapterDataBinding(
        @LayoutRes layoutId: Int,
        itemId: Int,
        onItemCreate: ((T) -> Unit)? = null,
        onItemBind: ((T, Int) -> Unit)? = null
    ): DataBindingRecyclerViewConfig<T> {
        return DataBindingRecyclerViewConfig(
            layoutId = layoutId,
            itemId = itemId,
            realisation = object : DataBindingAdapter<T> {
                override fun onCreate(binding: T) {
                    onItemCreate?.invoke(binding)
                }

                override fun onBind(binding: T, position: Int) {
                    onItemBind?.invoke(binding, position)
                }
            }
        )
    }

    private fun getKeyHandler(key: Int): RecyclerViewKeyHandler<*>? {
        return try {
            keyHandlers[key]
        } catch (e: Exception) {
            null
        }
    }

    protected open fun <Item : Any> initRecyclerViewKeyHandler(
        tabPosition: Int,
        recyclerView: RecyclerView,
        items: LiveData<List<Item>>,
        onClickHandler: ((Int) -> Unit)? = null
    ): RecyclerViewKeyHandler<Item> {
        val keyHandler = RecyclerViewKeyHandler(
            rv = recyclerView,
            items = items,
            lifecycleOwner = viewLifecycleOwner,
            initPosInfo = getKeyHandler(tabPosition)?.posInfo?.value,
            onClickPositionFunc = onClickHandler
        )

        getKeyHandler(tabPosition)?.clear()

        keyHandlers[tabPosition] = keyHandler

        return keyHandler
    }

    protected open fun onItemBindKeyHandlerHandler(
        bindItem: ViewBinding,
        position: Int,
        keyHandler: RecyclerViewKeyHandler<*>?
    ) {
        keyHandler?.let {
            bindItem.root.isSelected = it.isSelected(position)
        }
    }

    protected open fun onItemClickHandler(position: Int, keyHandler: RecyclerViewKeyHandler<*>?) {
        keyHandler?.onItemClicked(position)
    }

    protected fun getCurrentKeyHandler(): RecyclerViewKeyHandler<*>? {
        return vm.selectedPage.value?.let { position ->
            getKeyHandler(position)
        }
    }

    protected fun testOnScanResult(vmOnScanResult: (code: String) -> Unit) {
        val editText = EditText(context)
        val dialog = AlertDialog.Builder(context)
            .setTitle("onScanResult:")
            .setView(editText)
            .setPositiveButton("scan") { _, _ -> vmOnScanResult(editText.text.toString()) }
            .setNegativeButton("cancel") { _, _ -> }
        dialog.show()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int


    override fun onDestroy() {
        binding?.unbind()
        binding = null
        keyHandlers.forEach { it.value.clear() }
        keyHandlers.clear()
        super.onDestroy()
    }

    private fun getCoreMainActivity(): CoreMainActivity? {
        return activity?.implementationOf(CoreMainActivity::class.java)
    }
}