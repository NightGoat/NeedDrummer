package ru.nightgoat.needdrummer.core

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import ru.nightgoat.needdrummer.core.di.CoreComponent
import ru.nightgoat.needdrummer.core.di.CoreInjectHelper

abstract class CoreActivity<T : ViewDataBinding> : AppCompatActivity() {
    var binding: T? = null

    val coreComponent: CoreComponent by lazy {
        CoreInjectHelper.provideCoreComponent(this.applicationContext)
    }
}