package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projekpapb2.R
import com.example.projekpapb2.data.model.Booking
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.BookingRepository
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.components.NannyButton
import com.example.projekpapb2.ui.theme.Blue500
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavController, bookingId: String,nannyrepository: NannyRepository,bookingRepository: BookingRepository) {
    var reviewText by remember { mutableStateOf("") }
    var isSatisfied by remember { mutableStateOf<Boolean?>(null) }
    var booking by remember { mutableStateOf<Booking?>(null) }
    var nanny by remember { mutableStateOf<Nanny?>(null) }
    val db = FirebaseFirestore.getInstance()
    LaunchedEffect(bookingId) {
        val bookings = bookingRepository.getBookings()
        booking = bookings.find { it.id == bookingId }
        val nannies = nannyrepository.getNannies()
        nanny = nannies.find { it.id == booking?.nannyId }

    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {}, // Menghapus teks judul
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = "Back",
                            modifier = Modifier.size(34.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(19.dp))

                Box(
                    modifier = Modifier
                        .size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Blue600, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .background(Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(140.dp)
                                    .background(Color(0xFFECECEC), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(nanny?.photo),
                                    contentDescription = "Foto Nanny",
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(CircleShape)
                                        .border(6.dp, Color.White, CircleShape)
                                        .shadow(elevation = 8.dp, shape = CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }


                Text(
                    text = "Layanan Selesai\nBerikan Ulasanmu!",
                    style = TextStyle(
                        fontFamily = Fredoka,
                        fontSize = 20.sp,
                        color = Blue600,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Ulasan") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedLabelColor = Blue600,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {isSatisfied = true },
                        modifier = Modifier.size(58.dp)
                    ) {
                        if (isSatisfied == null || isSatisfied == true) {
                        Image(
                            painter = painterResource(id = R.drawable.like_icon),
                            contentDescription = "Like",
                            modifier = Modifier.size(55.dp)
                        )}
                    }
                    IconButton(
                        onClick = { isSatisfied = false},
                        modifier = Modifier.size(58.dp)
                    ) {
                        if (isSatisfied == null || isSatisfied == false) {
                            Image(
                                painter = painterResource(id = R.drawable.dislike_icon),
                                contentDescription = "Dislike",
                                modifier = Modifier.size(55.dp)
                            )}

                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                NannyButton(
                    text = "Selesai",
                    onClick = {
                        // Update booking status to "selesai"
                        val updatedBooking = booking?.copy(status = "selesai")
                        updatedBooking?.let {

                            val reviewData = mapOf(
                                "review" to reviewText,
                                "isSatisfied" to isSatisfied,
                                "status" to "selesai"
                            )
                            db.collection("bookings").document(bookingId)
                                .set(reviewData, SetOptions.merge())
                                .addOnSuccessListener {
                                    // Navigate to history screen after updating status
                                    navController.navigate("history") {
                                        popUpTo("review/$bookingId") { inclusive = true }
                                    }
                                }
                                .addOnFailureListener {
                                    // Handle failure
                                }
                        }
                    },
                    containerColor = Blue500,
                    contentColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

            }
        }
    }
}
