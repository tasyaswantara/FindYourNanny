package com.example.projekpapb2.data.repository

import com.example.projekpapb2.data.model.Nanny
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NannyRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val nannyCollection = firestore.collection("nannies")

    suspend fun getNannies(): List<Nanny> {
        return try {
            val result = nannyCollection.get().await()
            result.toObjects(Nanny::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
