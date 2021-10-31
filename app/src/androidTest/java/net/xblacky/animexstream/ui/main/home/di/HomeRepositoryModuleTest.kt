package net.xblacky.animexstream.ui.main.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.testing.BindValue
import dagger.hilt.testing.TestInstallIn
import net.xblacky.animexstream.ui.main.home.source.HomeFakeDefaultRepository
import net.xblacky.animexstream.ui.main.home.source.HomeRepository
import org.junit.Assert.*

import org.junit.Test
import javax.inject.Qualifier

//
//@Module
//@TestInstallIn(
//    components = [ViewModelComponent::class],
//    replaces = [HomeRepositoryModule::class]
//)
//abstract class HomeRepositoryModuleTest {
//
//
//    @Binds
//    @ViewModelScoped
//    @BindValue @HomeRepositoryModule.HomeRepo
//    abstract fun bindHomeRepo(homeRepo: HomeFakeDefaultRepository): HomeRepository
//}