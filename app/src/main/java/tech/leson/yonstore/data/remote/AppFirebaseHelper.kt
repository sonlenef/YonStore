package tech.leson.yonstore.data.remote

import android.net.Uri
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import tech.leson.yonstore.data.model.User

@Suppress("UNCHECKED_CAST")
class AppFirebaseHelper(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : FirebaseHelper {


    override fun register(registerData: User): Task<DocumentReference> {
        val user: Map<String, Any> =
            ObjectMapper().convertValue(registerData, Map::class.java) as Map<String, Any>
        return database.collection("users").add(user)
    }

    override fun getUser(uid: String): Task<QuerySnapshot> =
        database.collection("users").whereEqualTo("accountId", uid).get()

    override fun getAllCategory(): Task<QuerySnapshot> = database.collection("categories").get()

    override fun getLimitCategory(limit: Long): Task<QuerySnapshot> =
        database.collection("categories").limit(limit).get()

    override fun getCategoryByStyle(style: String): Task<QuerySnapshot> =
        database.collection("categories").whereEqualTo("style", style).get()

    override fun saveImage(file: Uri): Task<Uri> {
        val riversRef = storage.reference.child("images/${file.lastPathSegment}")
        return riversRef.putFile(file).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            riversRef.downloadUrl
        }
    }

    override fun deleteImage(url: String) = storage.getReferenceFromUrl(url).delete()

    override fun logoutFirebase() {
        auth.signOut()
    }
}
