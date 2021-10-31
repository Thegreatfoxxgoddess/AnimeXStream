package net.xblacky.animexstream

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.xblacky.animexstream.utils.preference.PreferenceHelper
import net.xblacky.animexstream.utils.realm.InitalizeRealm
import net.xblacky.animexstream.utils.rertofit.RetrofitHelper
import timber.log.Timber


@HiltAndroidApp
class AnimeXStream : Application() {

    override fun onCreate() {
        super.onCreate()
        InitalizeRealm.initializeRealm(this)
        PreferenceHelper(context = this)
        RetrofitHelper(PreferenceHelper.sharedPreference.getBaseUrl())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}