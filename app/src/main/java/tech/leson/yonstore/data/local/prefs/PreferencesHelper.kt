package tech.leson.yonstore.data.local.prefs

interface PreferencesHelper {
    fun setUserUid(uid: String)
    fun getUserUid(): String
}
