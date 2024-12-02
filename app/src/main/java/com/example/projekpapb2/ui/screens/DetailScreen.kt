package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.AuthRepository
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.components.TopAppBar
import com.example.projekpapb2.ui.utils.openWhatsApp

@Composable
fun DetailScreen(nannyId: String, navController: NavController, repository: NannyRepository) {
    val context = LocalContext.current
    var nanny by remember { mutableStateOf<Nanny?>(null) }

    LaunchedEffect(nannyId) {
        val nannies = repository.getNannies()
        nanny = nannies.find { it.id == nannyId }
    }
    val reviews = listOf(
        "Nanny Alina" to "Tepat waktu, sangat telaten dan bersih",
        "Nanny Zahra" to "Ramah, sabar, dan profesional",
        "Nanny Cinta" to "Pekerja keras dan detail",
        "Nanny Putri" to "Dapat menjaga anak dengan baik",
        "Nanny Bunga" to "Berpengalaman, sigap, dan jujur"
    )


    nanny?.let {
        Column(
            modifier = Modifier

                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            TopAppBar(navController = navController,title="Profil Pengurus")
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier

                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                // Profil
                ProfileImage(imageUrl = it.photo)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${it.age} Tahun",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tentang
            SectionHeader(title = "Tentang")
            Text(
                text = it.about,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Keahlian
            SectionHeader(title = "Keahlian")
            SkillChips(skills = it.skill)
            Spacer(modifier = Modifier.height(24.dp))

            // Pengalaman Kerja
            SectionHeader(title = "Pengalaman Kerja")
            it.workExperience.forEach { experience ->
                Text(
                    text = experience,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Ulasan
            SectionHeader(title = "Ulasan")
            reviews.forEach { review ->
                ReviewItem(reviewName = review.first, reviewText = review.second)
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Pesan Sekarang
            Button(
                onClick = { navController.navigate("booking/${it.id}") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Pesan Sekarang", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Composable
fun ReviewItem(reviewName: String, reviewText: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        Text(
            text = reviewName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = reviewText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ProfileImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(4.dp, Color.White, CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SkillChips(skills: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        skills.forEach { skill ->

            TextButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = skill,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}