package com.example.projekpapb2.data.model

import com.google.firebase.Timestamp

data class Booking(
    val id: String = "",
    val createdAt: Timestamp? = null, // Waktu pembuatan, nullable jika tidak langsung diinisialisasi
    val description: String = "", // Deskripsi booking
    val endTime: Timestamp? = null, // Waktu akhir booking
    val location: String = "", // Lokasi booking
    val nannyId: String = "", // ID pengasuh
    val startTime: Timestamp? = null, // Waktu mulai booking
    val status: String = "", // Status booking (misalnya: pending, confirmed)
    val title: String = "", // Judul booking
    val userId: String = "" // ID pengguna

)