package com.example.projekpapb2.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.projekpapb2.R
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.AuthRepository
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.components.TopAppBar
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka
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
Column( modifier = Modifier

    .fillMaxWidth(),
    horizontalAlignment = Alignment.Start) {
    TopAppBar(navController = navController,title="Profil Pengurus")
    nanny?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {


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
                    color = Blue600,
                    fontFamily = Fredoka,
                )
                Text(
                    text = "${it.age} Tahun",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    fontFamily = Fredoka,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tentang
            SectionHeader(title = "Tentang")
            Text(
                text = it.about,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Fredoka,

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
                    fontFamily = Fredoka,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Ulasan
            SectionHeader(title = "Ulasan")
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar item
            ){
                items(reviews) { review ->
                    ReviewItem(
                        reviewName = review.first,
                        reviewText = review.second
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Tombol Pesan Sekarang
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tombol Pesan Sekarang
                Button(
                    onClick = { navController.navigate("booking/${it.id}") },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                    shape = RoundedCornerShape(12.dp), // Membuat tombol rounded
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Pesan Sekarang",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = Fredoka
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Tombol Ikon Chat
                Box(
                    modifier = Modifier
                        .size(48.dp) // Ukuran gambar
                        .clickable {
                            // Membuka WhatsApp dengan nomor nanny
                            val whatsappIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://wa.me/${it.phoneNumber}")
                            )
                            context.startActivity(whatsappIntent)
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = "Chat",
                        modifier = Modifier.fillMaxSize()
                    )
                }
//                OutlinedButton(
//                    modifier = Modifier.size(48.dp) .border(
//                        width = 2.dp,
//                        color = Blue600,
//                        shape = RoundedCornerShape(16.dp)
//                    ),
//                    onClick = {
//                        // Membuka WhatsApp dengan nomor nanny
//                        val whatsappIntent = Intent(
//                            Intent.ACTION_VIEW,
//                            Uri.parse("https://wa.me/${it.phoneNumber}")
//                        )
//                        context.startActivity(whatsappIntent)
//                    },
//                    shape = RoundedCornerShape(12.dp), // Membuat outline button rounded
//
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.chat),
//                        contentDescription = "Chat",
//                        modifier = Modifier.size(32.dp) // Ukuran gambar di dalam tombol
//                    )
//                }
            }
        }

        }
    }
}




@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Color.Black,
        fontFamily = Fredoka,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Composable
fun ReviewItem(reviewName: String, reviewText: String) {
    Column (
        modifier = Modifier
            .background(Blue600.copy(alpha = 0.2f), MaterialTheme.shapes.large)
            .width(180.dp)
            .height(100.dp)
            .padding(12.dp)

    ) {
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = reviewName,
                style = MaterialTheme.typography.bodyMedium,
                color = Blue600,
                fontFamily = Fredoka,
            )
            AsyncImage(
                model = R.drawable.thumb,
                contentDescription = "Thumbs Up Icon",
                modifier = Modifier
                    .size(20.dp)
            )
        }


        Text(
            text = reviewText,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            fontFamily = Fredoka,
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
            .border(6.dp, Color.White, CircleShape)
            .shadow(elevation = 8.dp, shape = CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SkillChips(skills: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        skills.forEach { skill ->

            TextButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(
                        color = Blue600.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .padding(horizontal = 12.dp, vertical = 1.dp),
                contentPadding = PaddingValues(0.dp), // Menghapus padding bawaan
            ) {
                Text(
                    text = skill,
                    fontFamily = Fredoka,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Blue600
                )
            }
        }
    }
}