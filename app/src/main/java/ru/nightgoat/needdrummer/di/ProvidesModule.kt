package ru.nightgoat.needdrummer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.nightgoat.needdrummer.providers.AuthPreference
import ru.nightgoat.needdrummer.providers.IAuthPreference
import ru.nightgoat.needdrummer.repos.FireBaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IFirebaseRepo
import ru.nightgoat.needdrummer.repos.Interfaces.IResourcesRepo
import ru.nightgoat.needdrummer.repos.ResourcesRepo

@Module
@InstallIn(SingletonComponent::class)
class ProvidesModule {

    @Provides
    fun provideAuthPreference(@ApplicationContext applicationContext: Context): IAuthPreference {
        return AuthPreference(applicationContext)
    }

    @Provides
    fun provideResourcesRepo(@ApplicationContext applicationContext: Context): IResourcesRepo {
        return ResourcesRepo(applicationContext)
    }

    @Provides
    fun provideFirebase(stringResources: IResourcesRepo): IFirebaseRepo {
        return FireBaseRepo(stringResources)
    }
}