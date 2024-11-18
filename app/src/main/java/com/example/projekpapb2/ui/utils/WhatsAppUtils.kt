package com.example.projekpapb2.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openWhatsApp(context: Context, phoneNumber: String) {
    val url = "https://wa.me/$phoneNumber"
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(intent)
}
