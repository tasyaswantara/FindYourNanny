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
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Image
            Image(
                painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80"),
                contentDescription = "Nanny Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Name
            Text(
                text = nanny.name,
                style = TextStyle(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Experience
            Text(
                text = "${nanny.experience} Tahun Pengalaman",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Order Button
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF6672EE))
            ) {
                Text(
                    text = "Pesan Jasa",
                    style = TextStyle(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}
