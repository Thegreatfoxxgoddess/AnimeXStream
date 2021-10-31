package net.xblacky.animexstream.utils.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.xblacky.animexstream.BuildConfig
import net.xblacky.animexstream.utils.preference.Preference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Qualifier
    annotation class HttpBodyDebugger

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        //TODO Remove RXAdapter factory & Move to Coroutines
        sharedPreferences: Preference,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(sharedPreferences.getBaseUrl())
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
    }

    @Provides
    fun provideDebugOkHttpClient(@HttpBodyDebugger interceptor: HttpLoggingInterceptor): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        } else {
            OkHttpClient.Builder().build()
        }
    }

    @Provides
    @HttpBodyDebugger
    fun provideFullLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideRxJavaFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}
