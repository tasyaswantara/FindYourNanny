package com.example.projekpapb2.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilihJadwalScreen(nannyId: String, service:String, navController: NavController, repository: NannyRepository) {
    val context = LocalContext.current
    val currentUser = Firebase.auth.currentUser
    var address by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Int?>(null) }
    var selectedTime by remember { mutableStateOf<String?>(null) }
    var nanny by remember { mutableStateOf<Nanny?>(null) }
    val firestore = FirebaseFirestore.getInstance()

    LaunchedEffect(nannyId) {
        val nannies = repository.getNannies()
        nanny = nannies.find { it.id == nannyId }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        com.example.projekpapb2.ui.components.TopAppBar(navController = navController, "Jadwal")

        nanny?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))


                // Pilih Tanggal Pemesanan
                Text(
                    text = "Pilih Tanggal Pemesanan",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = Fredoka
                )

                CalendarComponent(
                    unavailableDates = listOf(4, 15, 25, 30),
                    onDateSelected = { selectedDate = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Pilih Waktu Pemesanan
                Text(
                    text = "Pilih Waktu Pemesanan",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = Fredoka
                )

                TimePickerComponent(
                    times = listOf("07.00", "10.00", "13.00", "15.00", "18.00", "20.00"),
                    onTimeSelected = { selectedTime = it }
                )

                Spacer(modifier = Modifier.height(24.dp))
                // Input untuk Alamat

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },

                    label = { Text("Alamat Rumah", fontFamily = Fredoka) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF6667FF),
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red,
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth().height(48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Konfirmasi Button
                    Button(
                        onClick = {
                            if (selectedDate != null && selectedTime != null) {
                                val calendar = Calendar.getInstance()
                                calendar.set(Calendar.DAY_OF_MONTH, selectedDate!!)
                                calendar.set(Calendar.MONTH, Calendar.OCTOBER) // Set bulan Oktober
                                calendar.set(Calendar.YEAR, 2024) // Contoh tahun, bisa dinamis

                                val timeParts = selectedTime!!.split(".")
                                calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
                                calendar.set(Calendar.MINUTE, timeParts[1].toInt())

                                val startMillis = calendar.timeInMillis
                                val endMillis =
                                    startMillis + (60 * 60 * 1000) // Durasi default 1 jam
                                val description =
                                    "Alamat Rumah: ${address},\nJenis layanan: ${service},\nNama nanny: ${it.name}"
                                addEventToCalendar(
                                    context = context,
                                    title = "Pemesanan Nanny ${it.name} untuk ${service}",
                                    location = address,
                                    description = description,
                                    startMillis = startMillis,
                                    endMillis = endMillis
                                )
                                // Simpan data ke Firestore
                                val bookingData = hashMapOf(
                                    "nannyId" to nannyId,
                                    "title" to "Pemesanan Nanny",
                                    "location" to address,
                                    "description" to "Pemesanan nanny di lokasi $address",
                                    "startTime" to Timestamp(java.util.Date(startMillis)),
                                    "endTime" to Timestamp(java.util.Date(endMillis)),
                                    "createdAt" to Timestamp.now(),
                                    "userId" to (currentUser?.uid ?: "User123"), // Ganti dengan ID pengguna autentik
                                    "status" to "berlangsung"
                                )

                                firestore.collection("bookings")
                                    .add(bookingData)
                                    .addOnSuccessListener {
                                        println("Booking berhasil ditambahkan ke Firestore")
                                        navController.navigate("home") // Navigasi ke halaman Home
                                    }
                                    .addOnFailureListener { e ->
                                        println("Gagal menambahkan booking: ${e.message}")
                                    }

                                navController.navigate("home")
                            }

                            // Menyimpan alamat ke Firestore
//                        val userId = auth.currentUser?.uid
//                        if (userId != null) {
//                            val userRef = firestore.collection("users").document(userId)
//                            userRef.update("address", address)
//                                .addOnSuccessListener {
//                                    // Alamat berhasil disimpan
//                                }
//                                .addOnFailureListener { e ->
//                                    // Gagal menyimpan alamat
//                                }
//                        }
//
//                        navController.navigate("home")

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                        shape = RoundedCornerShape(12.dp), // Membuat tombol rounded
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = selectedDate != null && selectedTime != null && address != "",

                        ) {
                        Text("Konfirmasi", color = Color.White, fontFamily = Fredoka)
                    }
                }
            }
        }

    }
}


@SuppressLint("NewApi")
@Composable
fun CalendarComponent(unavailableDates: List<Int>, onDateSelected: (Int) -> Unit) {
    val daysInMonth = (1..31).toList()
    var selectedDate by remember { mutableStateOf<Int?>(null) }
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.month
    val currentMonthName = currentMonth.getDisplayName(TextStyle.FULL, Locale("id", "ID"))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = currentMonthName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp),
            fontFamily = Fredoka
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Min", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = Fredoka)
            Text("Sen", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = Fredoka)
            Text("Sel", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = Fredoka)
            Text("Rab", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = Fredoka)
            Text("Kam", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = Fredoka)
            Text("Jum", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = Fredoka)
            Text("Sab", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = Fredoka)
        }

        Spacer(modifier = Modifier.height(8.dp))

        val rows = daysInMonth.chunked(7)
        for (row in rows) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                row.forEach { day ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = when {
                                    unavailableDates.contains(day) -> Color.Red
                                    selectedDate == day -> Color(0xFF758BFD)
                                    else -> Color.Transparent
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = !unavailableDates.contains(day)) {
                                selectedDate = day
                                onDateSelected(day)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.toString(),
                            color = if (unavailableDates.contains(day)) Color.White else Color.Black,
                            fontFamily = Fredoka
                        )
                    }
                }
            }
        }
    }
}
fun addEventToCalendar(
    context: Context,
    title: String,
    location: String,
    description: String,
    startMillis: Long,
    endMillis: Long,
) {
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        putExtra(CalendarContract.Events.DESCRIPTION, description)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
    }
    context.startActivity(intent)
}


@Composable
fun TimePickerComponent(times: List<String>, onTimeSelected: (String) -> Unit) {
    var selectedTime by remember { mutableStateOf<String?>(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Set jumlah kolom menjadi 3
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(times) { time ->
            Box(
                modifier = Modifier
                    .border(
                        width = if (selectedTime == time) 2.dp else 1.dp,
                        color = if (selectedTime == time) Color(0xFF758BFD) else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        selectedTime = time
                        onTimeSelected(time)
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = time, color = Color.Black, fontSize = 14.sp,fontFamily = Fredoka)
            }
        }
    }
}
