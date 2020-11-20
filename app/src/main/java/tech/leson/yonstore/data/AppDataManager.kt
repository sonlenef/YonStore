package tech.leson.yonstore.data

import android.net.Uri
import tech.leson.yonstore.data.local.prefs.PreferencesHelper
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.Product
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

    override fun getUser(uid: String) = mFirebaseHelper.getUser(uid)

    override fun getAllCategory() = mFirebaseHelper.getAllCategory()

    override fun getLimitCategory(limit: Long) = mFirebaseHelper.getLimitCategory(limit)

    override fun getCategoryByStyle(style: String) = mFirebaseHelper.getCategoryByStyle(style)

    override fun saveImage(file: Uri) = mFirebaseHelper.saveImage(file)

    override fun deleteImage(url: String) = mFirebaseHelper.deleteImage(url)

    override fun createProduct(product: Product) = mFirebaseHelper.createProduct(product)

    override fun getAllProduct() = mFirebaseHelper.getAllProduct()

    override fun getProductByCode(code: String) = mFirebaseHelper.getProductByCode(code)

    override fun getProductByCategory(category: Category) =
        mFirebaseHelper.getProductByCategory(category)

    override fun logoutFirebase() = mFirebaseHelper.logoutFirebase()

    override fun setUserUid(uid: String) {
        mPreferencesHelper.setUserUid(uid)
    }

    override fun getUserUid() = mPreferencesHelper.getUserUid()
}
