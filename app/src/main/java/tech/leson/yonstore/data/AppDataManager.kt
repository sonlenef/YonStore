package tech.leson.yonstore.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import tech.leson.yonstore.data.local.prefs.PreferencesHelper
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.data.remote.FirebaseHelper

class AppDataManager(firebaseHelper: FirebaseHelper, preferencesHelper: PreferencesHelper) :
    DataManager {

    private val mFirebaseHelper = firebaseHelper
    private val mPreferencesHelper = preferencesHelper

    override fun logout() {
        logoutFirebase()
        setUserUid("")
    }

    override fun register(registerData: User) = mFirebaseHelper.register(registerData)

    override fun getUser(uid: String): Task<QuerySnapshot> = mFirebaseHelper.getUser(uid)

    override fun getAllCategory(): Task<QuerySnapshot> = mFirebaseHelper.getAllCategory()

    override fun getLimitCategory(limit: Long): Task<QuerySnapshot> =
        mFirebaseHelper.getLimitCategory(limit)

    override fun getCategoryByStyle(style: String): Task<QuerySnapshot> =
        mFirebaseHelper.getCategoryByStyle(style)

    override fun saveImage(file: Uri): Task<Uri> = mFirebaseHelper.saveImage(file)

    override fun deleteImage(url: String): Task<Void> = mFirebaseHelper.deleteImage(url)

    override fun logoutFirebase() = mFirebaseHelper.logoutFirebase()

    override fun setUserUid(uid: String) {
        mPreferencesHelper.setUserUid(uid)
    }

    override fun getUserUid() = mPreferencesHelper.getUserUid()
}
