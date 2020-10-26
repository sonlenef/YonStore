package tech.leson.yonstore.data.remote

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import tech.leson.yonstore.data.model.User

@Suppress("UNCHECKED_CAST")
class AppFirebaseHelper(auth: FirebaseAuth, db: FirebaseFirestore) : FirebaseHelper {

    private val auth = auth
    private val database = db

    override fun register(registerData: User): Task<DocumentReference> {
        val user: Map<String, Any> =
            ObjectMapper().convertValue(registerData, Map::class.java) as Map<String, Any>
        return database.collection("users").add(user)
    }

    override fun getUser(uid: String): Task<QuerySnapshot> {
        return database.collection("users").whereEqualTo("accountId", uid).get()
    }

    override fun logoutFirebase() {
        auth.signOut()
    }
}
