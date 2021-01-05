package tech.leson.yonstore.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import tech.leson.yonstore.data.local.prefs.PreferencesHelper
import tech.leson.yonstore.data.model.*
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

    override fun updateUser(user: User) = mFirebaseHelper.updateUser(user)

    override fun getAllCategory() = mFirebaseHelper.getAllCategory()

    override fun getLimitCategory(limit: Long) = mFirebaseHelper.getLimitCategory(limit)

    override fun getCategoryByStyle(style: String) = mFirebaseHelper.getCategoryByStyle(style)

    override fun saveImage(file: Uri) = mFirebaseHelper.saveImage(file)

    override fun deleteImage(url: String) = mFirebaseHelper.deleteImage(url)

    override fun createProduct(product: Product) = mFirebaseHelper.createProduct(product)

    override fun getAllProduct() = mFirebaseHelper.getAllProduct()

    override fun getProductById(id: String) = mFirebaseHelper.getProductById(id)

    override fun getProductByCode(code: String) = mFirebaseHelper.getProductByCode(code)

    override fun getProductByCategory(category: Category) =
        mFirebaseHelper.getProductByCategory(category)

    override fun updateProduct(product: Product) = mFirebaseHelper.updateProduct(product)

    override fun removeProduct(product: Product) = mFirebaseHelper.removeProduct(product)

    override fun searchProduct(searchData: String) = mFirebaseHelper.searchProduct(searchData)

    override fun onOrder(order: Order) = mFirebaseHelper.onOrder(order)

    override fun getOrderByUserId(userId: String) = mFirebaseHelper.getOrderByUserId(userId)

    override fun createEvent(event: Event) = mFirebaseHelper.createEvent(event)

    override fun getAllEvent() = mFirebaseHelper.getAllEvent()

    override fun createReview(review: Review) = mFirebaseHelper.createReview(review)

    override fun getReviewByProductId(productId: String) = mFirebaseHelper.getReviewByProductId(productId)

    override fun logoutFirebase() = mFirebaseHelper.logoutFirebase()

    override fun setUserUid(uid: String) {
        mPreferencesHelper.setUserUid(uid)
    }

    override fun getUserUid() = mPreferencesHelper.getUserUid()
}
