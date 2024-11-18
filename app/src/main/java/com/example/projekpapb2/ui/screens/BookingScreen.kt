package com.example.projekpapb2.ui.screens
import android.accounts.Account
import android.accounts.AccountManager
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.example.projekpapb2.data.repository.AuthRepository
import com.example.projekpapb2.ui.utils.addEventToCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun BookingScreen(navController: NavController) {
    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var selectedStartTime by remember { mutableStateOf<Date?>(null) }
    var selectedEndTime by remember { mutableStateOf<Date?>(null) }

    // Formatter for date and time
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    Column(modifier = Modifier.padding(16.dp)) {
        // Address Input
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Date Picker
        Button(onClick = {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, month, dayOfMonth)
                    selectedDate = newDate.time
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = selectedDate?.let { "Tanggal: ${dateFormatter.format(it)}" } ?: "Pilih Tanggal")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Start Time Picker
        Button(onClick = {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    val newTime = Calendar.getInstance()
                    newTime.time = selectedDate ?: Date()
                    newTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    newTime.set(Calendar.MINUTE, minute)
                    selectedStartTime = newTime.time
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = selectedStartTime?.let { "Mulai: ${timeFormatter.format(it)}" } ?: "Pilih Waktu Mulai")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // End Time Picker
        Button(onClick = {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    val newTime = Calendar.getInstance()
                    newTime.time = selectedDate ?: Date()
                    newTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    newTime.set(Calendar.MINUTE, minute)
                    selectedEndTime = newTime.time
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = selectedEndTime?.let { "Selesai: ${timeFormatter.format(it)}" } ?: "Pilih Waktu Selesai")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(onClick = {
            if (selectedStartTime != null && selectedEndTime != null) {
                addEventToCalendar(
                    context = context,
                    title = "Pesanan Nanny",
                    location = address,
                    description = "Pesanan telah dibuat",
                    startMillis = selectedStartTime!!.time,
                    endMillis = selectedEndTime!!.time
                )
                navController.navigate("home")
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Pesan")
        }
    }
}