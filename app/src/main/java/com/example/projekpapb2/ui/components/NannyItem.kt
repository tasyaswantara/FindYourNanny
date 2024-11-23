package com.example.projekpapb2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.projekpapb2.data.model.Nanny

@Composable
fun NannyItem(nanny: Nanny, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp), // Drop shadow
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)) // Card background color #FFFFFF
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Gambar


            Image(
                painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80"),
                contentDescription = "Nanny Image",
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nama Nanny dengan font Fredoka Medium 14
            Text(
                text = nanny.name,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Pengalaman Nanny dengan font Fredoka Regular 10
            Text(
                text = "Pengalaman: ${nanny.experience} tahun",
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            )
            // Usia dan Jenis Kelamin
            Text(
                text = "Usia: ${nanny.age} tahun",
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            )
            Text(
                text = "Jenis Kelamin: ${nanny.gender}",
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tombol Pesan Jasa dengan warna dan font sesuai permintaan
            Button(
                onClick = { onClick() },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF6672EE))
            ) {
                Text(
                    text = "Pesan Jasa",
                    style = TextStyle(
                        fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif, // Font Fredoka Medium 8
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                        fontSize = 8.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}
