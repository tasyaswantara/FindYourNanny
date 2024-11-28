package com.example.projekpapb2.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.projekpapb2.R

data class OnBoardingInterface(
    val title:String,
    val description:String,
    @DrawableRes val image: Int

)

val pages = listOf(
    OnBoardingInterface(
        title = "Selamat datang di Find Your Nanny!",
        description = "Aplikasi Find Your Nanny memudahkan Anda menemukan tenaga bantuan rumah tangga yang berpengalaman dan terpercaya.",
        image = R.drawable.intro_satu
    ),
    OnBoardingInterface(
        title = "Keamanan dan Kepercayaan Terjamin",
        description = "Setiap tenaga bantuan telah melalui proses verifikasi ketat untuk memberikan rasa aman.",
        image = R.drawable.intro_dua
    ),
    OnBoardingInterface(
        title = "Lihat Ulasan dan Rekomendasi Pengguna Lainnya",
        description = "Dapatkan tenaga bantuan terbaik berdasarkan ulasan dan rekomendasi dari pengguna lainnya.",
        image = R.drawable.intro_tiga
    ),
)

