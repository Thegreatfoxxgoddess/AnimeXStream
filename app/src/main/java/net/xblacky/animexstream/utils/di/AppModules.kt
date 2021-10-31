package net.xblacky.animexstream.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.xblacky.animexstream.utils.constants.C
import net.xblacky.animexstream.utils.preference.Preference
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModules {

    annotation class RequestHeader

    @Provides
    @RequestHeader
    @Singleton
    fun provideNetworkHeader(preference: Preference): Map<String, String> {
        return mapOf(
            "referer" to preference.getReferrer(),
            "origin" to preference.getOrigin(),
            "user-agent" to C.USER_AGENT
        )
    }
}