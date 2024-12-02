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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.ui.theme.Fredoka

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

        ) {
            // Image
            Image(
                painter = rememberAsyncImagePainter(nanny.photo),
                contentDescription = "Nanny Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            ){
                // Name
                Text(
                    text = nanny.name,
                    style = TextStyle(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = Fredoka,

                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Experience
                Text(
                    text = "${nanny.experience} Tahun Pengalaman",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = Fredoka,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Order Button
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF6672EE)),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Pesan Jasa",
                        style = TextStyle(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                            fontSize = 12.sp,
                            color = Color.White,
                            fontFamily = Fredoka,
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}
