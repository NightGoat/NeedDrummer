package ru.nightgoat.needdrummer.core

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class CoreActivity<T : ViewDataBinding> : AppCompatActivity() {
    var binding: T? = null
}