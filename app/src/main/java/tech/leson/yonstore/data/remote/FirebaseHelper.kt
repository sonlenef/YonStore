package tech.leson.yonstore.data.remote

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import tech.leson.yonstore.data.model.*

interface FirebaseHelper {
    fun register(registerData: User): Task<DocumentReference>
    fun getUser(uid: String): Task<QuerySnapshot>
    fun updateUser(user: User): Task<Void>
    fun getAllCategory(): Task<QuerySnapshot>
    fun getLimitCategory(limit: Long): Task<QuerySnapshot>
    fun getCategoryByStyle(style: String): Task<QuerySnapshot>
    fun saveImage(file: Uri): Task<Uri>
    fun deleteImage(url: String): Task<Void>
    fun createProduct(product: Product): Task<DocumentReference>
    fun getAllProduct(): Task<QuerySnapshot>
    fun getProductById(id: String): Task<DocumentSnapshot>
    fun getProductByCode(code: String): Task<QuerySnapshot>
    fun getProductByCategory(category: Category): Task<QuerySnapshot>
    fun updateProduct(product: Product): Task<Void>
    fun removeProduct(product: Product): Task<Void>
    fun searchProduct(searchData: String): Task<QuerySnapshot>
    fun onOrder(order: Order): Task<DocumentReference>
    fun getOrderByUserId(userId: String): Task<QuerySnapshot>
    fun createEvent(event: Event): Task<DocumentReference>
    fun getAllEvent(): Task<QuerySnapshot>
    fun logoutFirebase()
}
