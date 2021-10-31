package net.xblacky.animexstream.ui.main.home.di


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.xblacky.animexstream.ui.main.home.source.*
import net.xblacky.animexstream.ui.main.home.source.local.HomeLocalRepository
import net.xblacky.animexstream.ui.main.home.source.remote.HomeRemoteRepository
import net.xblacky.animexstream.utils.rertofit.NetworkInterface
import retrofit2.Retrofit
import javax.inject.Qualifier

@InstallIn(ViewModelComponent::class)
@Module
object HomeNetworkDI {

    @Provides
    @ViewModelScoped
    fun provideHomeNetworkService(retrofit: Retrofit): NetworkInterface.HomeDataService {
        return retrofit.create(NetworkInterface.HomeDataService::class.java)
    }

}


@InstallIn(ViewModelComponent::class)
@Module
abstract class HomeRepositoryModule {

    @Qualifier
    annotation class LocalRepo

    @Qualifier
    annotation class RemoteRepo

    @Qualifier
    annotation class HomeRepo

    @LocalRepo
    @ViewModelScoped
    @Binds
    abstract fun bindLocalRepo(localRepo: HomeLocalRepository): HomeDataSource

    @RemoteRepo
    @ViewModelScoped
    @Binds
    abstract fun bindRemoteRepo(remoteRepo: HomeRemoteRepository): HomeDataSource

    @HomeRepo
    @Binds
    @ViewModelScoped
    abstract fun bindHomeRepo(homeRepo: HomeDefaultRepository): HomeRepository
}