package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.AuthRepository
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.utils.openWhatsApp

@Composable
fun DetailScreen(nannyId: String, navController: NavController, repository: NannyRepository) {
    val context = LocalContext.current
    var nanny by remember { mutableStateOf<Nanny?>(null) }

    LaunchedEffect(nannyId) {
        val nannies = repository.getNannies()
        nanny = nannies.find { it.id == nannyId }
    }

    nanny?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nama: ${it.name}")
            Text("Pengalaman: ${it.experience} tahun")
            Text("Usia: ${it.age} tahun")
            Text("Jenis Kelamin: ${it.gender}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                openWhatsApp(context, it.phoneNumber)
            }) {
                Text("Hubungi via WhatsApp")
            }
            Button(onClick = { navController.navigate("booking/${it.id}") }) {
                Text("Pesan Nanny")
            }
        }
    }
}
