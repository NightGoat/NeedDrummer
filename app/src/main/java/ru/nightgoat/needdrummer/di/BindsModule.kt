package ru.nightgoat.needdrummer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.nightgoat.needdrummer.data.repos.AuthRepo
import ru.nightgoat.needdrummer.data.repos.IAuthRepo
import ru.nightgoat.needdrummer.domain.auth.*

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindCheckEmailUseCase(it: CheckEmailUseCase): ICheckEmailUseCase

    @Binds
    abstract fun bindForgotPasswordUseCase(it: ForgotPasswordUseCase): IForgotPasswordUseCase

    @Binds
    abstract fun bindCheckPasswordUseCase(it: CheckPasswordUseCase): ICheckPasswordUseCase

    @Binds
    abstract fun bindCheckNameUseCase(it: CheckNameUseCase): ICheckNameUseCase

    @Binds
    abstract fun bindLoginUseCase(it: LoginUseCase): ILoginUseCase

    @Binds
    abstract fun bindRegisterUseCase(it: RegisterUseCase): IRegisterUseCase

    @Binds
    abstract fun bindAuthRepo(it: AuthRepo) : IAuthRepo
}