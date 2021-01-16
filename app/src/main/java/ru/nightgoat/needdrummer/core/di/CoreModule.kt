package ru.nightgoat.needdrummer.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.nightgoat.needdrummer.core.platform.CoreNavigator
import ru.nightgoat.needdrummer.core.platform.ICoreNavigator
import ru.nightgoat.needdrummer.core.platform.navigation.BackFragmentResultHelper
import javax.inject.Singleton

@Module(includes = [CoreModule.Declarations::class])
class CoreModule(val application: Application) {

    @Module
    internal interface Declarations {
        @Binds
        @Singleton
        fun bindICoreNavigator(realisation: CoreNavigator): ICoreNavigator
    }

    @Provides
    fun provideAppContext() = application.applicationContext!!

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    internal fun provideBackResultHelper(): BackFragmentResultHelper {
        return BackFragmentResultHelper()
    }

    companion object {
        val DB_PATH = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath}/db"
    }
}