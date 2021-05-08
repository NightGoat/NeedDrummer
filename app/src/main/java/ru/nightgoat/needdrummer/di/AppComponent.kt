package ru.nightgoat.needdrummer.di

import dagger.Component
import ru.nightgoat.needdrummer.activity.MainActivity
import ru.nightgoat.needdrummer.features.findBand.FindBandViewModel

@Component
@AppScope
interface AppComponent  {

    fun inject(mainActivity: MainActivity)

    fun inject(it: FindBandViewModel)
}