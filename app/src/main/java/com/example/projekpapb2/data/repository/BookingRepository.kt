package com.example.projekpapb2.data.repository

import com.example.projekpapb2.data.model.Booking
import com.example.projekpapb2.data.model.Nanny
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BookingRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val bookingCollection = firestore.collection("bookings")

    suspend fun getBookings(): List<Booking> {
        return try {
            val result = bookingCollection.get().await()
            result.toObjects(Booking::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

}