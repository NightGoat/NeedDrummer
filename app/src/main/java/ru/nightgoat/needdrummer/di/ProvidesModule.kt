package ru.nightgoat.needdrummer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.nightgoat.needdrummer.data.base.IFirebaseRepo
import ru.nightgoat.needdrummer.data.base.IStringResources
import ru.nightgoat.needdrummer.data.sources.local.AuthPreference
import ru.nightgoat.needdrummer.data.sources.local.IAuthPreference
import ru.nightgoat.needdrummer.data.sources.remote.FireBaseRepo
import ru.nightgoat.needdrummer.providers.StringResources

@Module
@InstallIn(SingletonComponent::class)
class ProvidesModule {

    @Provides
    fun provideAuthPreference(@ApplicationContext applicationContext: Context): IAuthPreference {
        return AuthPreference(applicationContext)
    }

    @Provides
    fun provideResourcesRepo(@ApplicationContext applicationContext: Context): IStringResources {
        return StringResources(applicationContext)
    }

    @Provides
    fun provideFirebase(stringResources: IStringResources): IFirebaseRepo {
        return FireBaseRepo(stringResources)
    }
}