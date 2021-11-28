package ru.nightgoat.needdrummer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.nightgoat.kextensions.utils.Kex

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Kex.setTimber()
    }
}