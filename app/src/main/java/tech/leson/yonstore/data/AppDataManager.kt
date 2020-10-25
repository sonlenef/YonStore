package tech.leson.yonstore.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import tech.leson.yonstore.data.local.prefs.PreferencesHelper
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.data.remote.FirebaseHelper

class AppDataManager(firebaseHelper: FirebaseHelper, preferencesHelper: PreferencesHelper) :
    DataManager {

    private val mFirebaseHelper = firebaseHelper
    private val mPreferencesHelper = preferencesHelper

    override fun register(registerData: User) = mFirebaseHelper.register(registerData)

    override fun getUser(uid: String): Task<QuerySnapshot> = mFirebaseHelper.getUser(uid)

    override fun setUserUid(uid: String) {
        mPreferencesHelper.setUserUid(uid)
    }

    override fun getUserUid() = mPreferencesHelper.getUserUid()
}
