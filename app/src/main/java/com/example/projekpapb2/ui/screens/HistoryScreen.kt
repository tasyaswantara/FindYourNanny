package com.example.projekpapb2.ui.screens

import com.example.projekpapb2.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projekpapb2.ui.components.BottomNavbar
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka

@Composable
fun HistoryScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("Berlangsung") }

    Scaffold(
        topBar = {
            com.example.projekpapb2.ui.components.TopAppBar(
                navController = navController,
                title = "Riwayat"
            )
        },
        bottomBar = {
            BottomNavbar(
                navController = navController,
                selectedScreen = "Riwayat",
                onItemSelected = { selected ->
                    println("Selected screen: $selected")
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Tab Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { selectedTab = "Berlangsung" },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedTab == "Berlangsung") Color(0xFFECECEC) else Color.Transparent,
                        contentColor = Color(0xFF7C83FD)
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "Berlangsung",
                        style = TextStyle(fontWeight = FontWeight.Medium, fontFamily = Fredoka)
                    )
                }

                OutlinedButton(
                    onClick = { selectedTab = "Selesai" },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedTab == "Selesai") Color(0xFFECECEC) else Color.Transparent,
                        contentColor = Color(0xFF7C83FD)
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "Selesai",
                        style = TextStyle(fontWeight = FontWeight.Medium, fontFamily = Fredoka)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content Based on Tab
            if (selectedTab == "Berlangsung") {
                OngoingServices()
            } else {
                CompletedServices()
            }
        }
    }
}

@Composable
fun OngoingServices() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(3) {
            ServiceCard(
                name = "Rani Maharani",
                date = "Februari 20, 2024",
                status = "Layanan sedang berlangsung",
                statusColor = Color(0xFF4CAF50),
                showButtons = false, // Tombol Like tidak ditampilkan
                showCompleteButton = true // Tombol Selesai hanya muncul di tab Berlangsung
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CompletedServices() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(2) {
            ServiceCard(
                name = "Rani Maharani",
                date = "Februari 20, 2024",
                status = "Layanan Selesai",
                statusColor = if (it % 2 == 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                showButtons = true, // Tombol Like ditampilkan di tab Selesai
                showCompleteButton = false // Tombol "Selesai" tidak muncul di tab Selesai
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ServiceCard(
    name: String,
    date: String,
    status: String,
    statusColor: Color,
    showButtons: Boolean, // Tombol Like di sebelah kiri status
    showCompleteButton: Boolean = false // Tombol Selesai hanya di tab Berlangsung
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .background(Color(0xFF7C83FD), shape = RoundedCornerShape(12.dp)) // Outline biru
            .padding(2.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White) // Latar Card putih
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Foto
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.foto_nanny),
                        contentDescription = "Foto Nanny"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Informasi Layanan
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF7C83FD),
                        fontFamily = Fredoka // Ganti font ke Fredoka
                    )
                    Text(
                        text = date,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = Fredoka // Ganti font ke Fredoka
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Tombol Like di sebelah kiri status
                        if (showButtons) {
                            IconButton(
                                onClick = { /* Handle Like */ },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.like_icon),
                                    contentDescription = "Like",
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                        }

                        // Status layanan
                        Text(
                            text = status,
                            fontSize = 14.sp,
                            color = statusColor,
                            style = TextStyle(fontWeight = FontWeight.Medium, fontFamily = Fredoka)
                        )
                    }
                }

                // Tombol "Selesai" kecil di sebelah kanan hanya di tab Berlangsung
                if (showCompleteButton) {
                    Button(
                        onClick = { /* Belum diarahkan */ },
                        modifier = Modifier
                            .height(36.dp)
                            .wrapContentWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C83FD)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Selesai", color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
