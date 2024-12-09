package com.example.projekpapb2.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    fun register(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()

                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                        if (profileTask.isSuccessful) {
                            val userData = mapOf("phoneNumber" to phoneNumber)
                            db.collection("users").document(user.uid)
                                .set(userData, SetOptions.merge()) // Gunakan merge untuk hanya mengubah atribut tertentu
                                .addOnSuccessListener {
                                   //
                                }
                                .addOnFailureListener {
                                   //
                                }
                            onSuccess()
                        } else {
                            onError(profileTask.exception?.message ?: "Gagal memperbarui profil")
                        }
                    }
                } else {
                    onError(task.exception?.message ?: "Registrasi gagal")
                }
            }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it.message ?: "Error") }
    }
}
