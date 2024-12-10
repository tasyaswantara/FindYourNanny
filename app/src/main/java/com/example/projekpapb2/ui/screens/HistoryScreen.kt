package com.example.projekpapb2.ui.screens

import com.example.projekpapb2.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projekpapb2.data.model.Booking
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.BookingRepository
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.components.BottomNavbar
import com.example.projekpapb2.ui.theme.Fredoka
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryScreen(navController: NavController, repository: BookingRepository,nannyrepository:NannyRepository) {
    var ongoingBookings by remember { mutableStateOf(emptyList<Booking>()) }
    var completedBookings by remember { mutableStateOf(emptyList<Booking>()) }
    var nannies by remember { mutableStateOf(emptyList<Nanny>()) }
    var selectedTab by remember { mutableStateOf("Berlangsung") }
    val currentUser = Firebase.auth.currentUser
    LaunchedEffect(currentUser) {

        if (currentUser != null) {
            nannies = nannyrepository.getNannies()
            val allBookings = repository.getBookings()
            ongoingBookings = allBookings
                .filter { booking ->
                booking.userId == currentUser.uid && booking.status == "berlangsung"
            }
            completedBookings = allBookings.filter { booking ->
                booking.userId == currentUser.uid && booking.status == "selesai"
            }
        } else {
            ongoingBookings = emptyList()
            completedBookings = emptyList()
        }
    }

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
                OngoingServices(ongoingBookings,nannies, navController = navController)
            } else {
                CompletedServices(completedBookings,nannies,navController = navController)
            }
        }
    }
}

@Composable
fun OngoingServices(ongoingBookings: List<Booking>, nannies: List<Nanny>, navController: NavController) {
    val dateFormatter = SimpleDateFormat("dd MMMM", Locale("id", "ID"))

    LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        item {
            if (ongoingBookings.isEmpty()) {
                Text(
                    text = "Belum ada riwayat berlangsung",
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        items(ongoingBookings) { booking ->
            val nanny = nannies.find { it.id == booking.nannyId }
            val formattedDate = booking.startTime?.toDate()?.let { dateFormatter.format(it) }
                ?: "Tanggal tidak tersedia"
            ServiceCard(
                bookingId = booking.id,
                name = nanny?.name ?: "Nama tidak ditemukan",
                date = formattedDate,
                status = "Layanan sedang berlangsung",
                statusColor = Color(0xFF4CAF50),
                showButtons = false,
                showCompleteButton = true,
                photo = nanny?.photo ?: "",
                navController = navController,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun CompletedServices(completedBookings: List<Booking>, nannies: List<Nanny>, navController: NavController) {
    val dateFormatter = SimpleDateFormat("dd MMMM", Locale("id", "ID"))

    LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        item {
            if (completedBookings.isEmpty()) {
                Text(
                    text = "Belum ada riwayat selesai",
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        items(completedBookings) { booking ->
            val nanny = nannies.find { it.id == booking.nannyId }
            val formattedDate = booking.startTime?.toDate()?.let { dateFormatter.format(it) }
                ?: "Tanggal tidak tersedia"
            ServiceCard(
                bookingId = booking.id,
                name = nanny?.name ?: "Nama tidak ditemukan",
                date = formattedDate,
                status = "Layanan selesai",
                statusColor = Color.Black,
                showButtons = true,
                showCompleteButton = false,
                photo = nanny?.photo ?: "",
                navController = navController,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ServiceCard(
    bookingId:String,
    navController: NavController,
    name: String,
    date: String,
    photo:String,
    status: String,
    statusColor: Color,
    showButtons: Boolean,
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
                        painter = rememberAsyncImagePainter(photo),
                        contentDescription = "Nanny Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
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
                        onClick = {navController.navigate("review/${bookingId}") },
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
