package ru.nightgoat.needdrummer.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import ru.nightgoat.needdrummer.BR
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.activity.MainActivity
import ru.nightgoat.needdrummer.core.platform.models.IResult
import ru.nightgoat.needdrummer.core.platform.models.SResult
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

    private val contentView: View?
        get() = this.view

    /**
     * Content Layout
     */
    private val contentViewLayout: View?
        get() = contentView?.findViewById(R.id.contentLayout)

    /**
     * Loading Layout
     */
    private val loadingViewLayout: View?
        get() = contentView?.findViewById(R.id.loadingLayout)

    /**
     * Error Layout
     */
    private val errorViewLayout: View?
        get() = contentView?.findViewById(R.id.errorLayout)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        observeResult()
    }

    private fun handleToast(result: SResult.Toast) {
        showShortToast(result.message)
    }

    private fun observeResult() {
        lifecycleScope.launchWhenStarted {
            vm.resultFlow.collect {
                if (!it.isHandled) {
                    handleResult(it)
                }
            }
        }
    }

    open fun handleSuccess(result: IResult<Any>) = Unit

    private fun handleResult(result: IResult<Any>) {
        if (result.isHandled) return
        result.handle()

        when (result) {
            is SResult.Loading -> showLayoutLoading()
            is SResult.NavigateResult -> handleNavigation(result)
            is SResult.Toast -> handleToast(result)
            is SResult.ErrorResult -> handleError(result)
            is SResult.Success -> {
                hideLayoutLoading()
                handleSuccess(result)
            }
        }
    }

    open fun handleError(error: SResult.ErrorResult) {
        showShortToast(error.message)
    }

    private fun handleNavigation(result: SResult.NavigateResult) {
        hideLayoutLoading()
        when (result) {
            is SResult.NavigateResult.NavigateTo -> {
                val direction = result.direction
                navigateTo(direction)
            }
            is SResult.NavigateResult.NavigateBy -> navigateBy(result.navigateResourceId)
            is SResult.NavigateResult.NavigatePopTo -> navigatePopTo(
                result.navigateResourceId,
                result.isInclusive
            )

            SResult.NavigateResult.NavigateBack -> onBackPressed()
            SResult.NavigateResult.NavigateNext -> onNextPressed()
            SResult.NavigateResult.Stay -> Unit
        }
    }

    private fun showLayoutLoading() {
        hideKeyboard()
        errorViewLayout?.hide()
        contentViewLayout?.hide()
        loadingViewLayout?.show()
    }

    open fun onBackPressed(): Boolean {
        return if (!canPressBack) true else navController.popBackStack()
    }

    private fun hideLayoutLoading() {
        hideKeyboard()
        errorViewLayout?.hide()
        loadingViewLayout?.hide()
        contentViewLayout?.show()
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

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onDestroy() {
        binding?.unbind()
        binding = null
        keyHandlers.forEach { it.value.clear() }
        keyHandlers.clear()
        super.onDestroy()
    }

    private fun getCoreMainActivity(): MainActivity? {
        return activity?.implementationOf(MainActivity::class.java)
    }
}