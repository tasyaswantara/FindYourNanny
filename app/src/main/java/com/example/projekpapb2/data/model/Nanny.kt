package com.example.projekpapb2.data.model
import com.example.projekpapb2.R

data class Nanny(
    val id: String = "",
    val name: String = "",
    val experience: Int = 0,
    val age: Int = 0,
    val gender: String = "",
    val phoneNumber: String = "",
    val photo: String = "",
    val about: String = "",
    val skill: List<String> = emptyList(),
    val workExperience: List<String> = emptyList(),
    val jenisLayanan: List<String> = emptyList(),
)
