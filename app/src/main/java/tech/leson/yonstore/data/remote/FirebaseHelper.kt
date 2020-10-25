package tech.leson.yonstore.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import tech.leson.yonstore.data.model.User

interface FirebaseHelper {
    fun register(registerData: User): Task<DocumentReference>
    fun getUser(uid: String): Task<QuerySnapshot>
}