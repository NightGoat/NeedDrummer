package ru.nightgoat.needdrummer.core.utilities.extentions

import ru.nightgoat.needdrummer.MainActivity
import ru.nightgoat.needdrummer.core.CoreFragment
import ru.nightgoat.needdrummer.di.AppComponent

fun CoreFragment<*, *>.getAppComponent(): AppComponent? {
    return activity.implementationOf(MainActivity::class.java)?.appComponent
}