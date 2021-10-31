package net.xblacky.animexstream.utils.preference

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.xblacky.animexstream.utils.constants.C
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class Preference @Inject constructor(@ApplicationContext val context: Context) {
    private val PREF_NAME = "AnimeXStream"
    private val BASE_URL = "BASE_URL"
    private val ORIGIN = "ORIGIN"
    private val REFERER = "REFERER"
    private val PRIVATE_MODE = 0
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

    fun getBaseUrl(): String {
        return sharedPreferences.getString(BASE_URL, C.BASE_URL) ?: C.BASE_URL
    }

    fun setBaseUrl(baseUrl: String) {
        val editor = sharedPreferences.edit()
        editor.putString(BASE_URL, baseUrl)
        editor.apply()
    }


    fun getOrigin(): String {
        return sharedPreferences.getString(ORIGIN, C.ORIGIN) ?: C.ORIGIN
    }

    fun setOrigin(origin: String) {
        val editor = sharedPreferences.edit()
        editor.putString(ORIGIN, origin)
        editor.apply()
    }

    fun getReferrer(): String {
        return sharedPreferences.getString(REFERER, C.REFERER) ?: C.REFERER
    }

    fun setReferrer(ref: String) {
        val editor = sharedPreferences.edit()
        editor.putString(REFERER, ref)
        editor.apply()
    }

}