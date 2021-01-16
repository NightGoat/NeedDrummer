package ru.nightgoat.needdrummer.core

import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.platform.navigation.FragmentStack
import ru.nightgoat.needdrummer.databinding.ActivityMainBinding

class CoreMainActivity : CoreActivity<ActivityMainBinding>(){

    val fragmentStack: FragmentStack by lazy {
        FragmentStack(supportFragmentManager, R.id.fragments).apply {
            coreComponent.inject(this)
        }
    }
}