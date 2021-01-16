package ru.nightgoat.needdrummer.di

import dagger.Component
import ru.nightgoat.needdrummer.MainActivity
import ru.nightgoat.needdrummer.core.di.CoreComponent
import ru.nightgoat.needdrummer.features.findBand.FindBandViewModel

@Component(dependencies = [CoreComponent::class])
@AppScope
interface AppComponent : CoreComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(it: FindBandViewModel)
}