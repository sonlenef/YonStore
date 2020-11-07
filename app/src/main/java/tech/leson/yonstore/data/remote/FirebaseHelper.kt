package tech.leson.yonstore.data.remote

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import tech.leson.yonstore.data.model.User

interface FirebaseHelper {
    fun register(registerData: User): Task<DocumentReference>
    fun getUser(uid: String): Task<QuerySnapshot>
    fun getAllCategory(): Task<QuerySnapshot>
    fun getLimitCategory(limit: Long): Task<QuerySnapshot>
    fun getCategoryByStyle(style: String): Task<QuerySnapshot>
    fun saveImage(file: Uri): Task<Uri>
    fun deleteImage(url: String): Task<Void>
    fun logoutFirebase()
}
