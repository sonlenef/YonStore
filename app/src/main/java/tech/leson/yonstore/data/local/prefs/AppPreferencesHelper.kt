package tech.leson.yonstore.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import tech.leson.yonstore.di.PreferenceInfo

class AppPreferencesHelper(context: Context, @PreferenceInfo prefFileName: String) :
    PreferencesHelper {

    companion object {
        private const val PREF_KEY_USER_UID = "PREF_KEY_USER_UID"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun setUserUid(uid: String) {
        mPrefs.edit().putString(PREF_KEY_USER_UID, uid).apply()
    }

    override fun getUserUid()= mPrefs.getString(PREF_KEY_USER_UID, "")!!
}
