package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.components.NannyButton
import com.example.projekpapb2.ui.components.TopAppBar
import com.example.projekpapb2.ui.theme.Blue200
import com.example.projekpapb2.ui.theme.Blue400
import com.example.projekpapb2.ui.theme.Blue500
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInformationScreen(navController: NavController) {
    val currentUser = Firebase.auth.currentUser
    val db = FirebaseFirestore.getInstance()
    var showSuccessPopup by remember { mutableStateOf(false) }
    var showFailurePopup by remember { mutableStateOf(false) }
    // State untuk data pengguna
    var displayName by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var phoneNumber by remember { mutableStateOf("") }
    val profileImageUrl = currentUser?.photoUrl
    var showDeleteAccountPopup by remember { mutableStateOf(false) }

    // Fungsi untuk memuat data dari Firestore
    fun loadUserData() {
        currentUser?.uid?.let { uid ->
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        phoneNumber = document.getString("phoneNumber") ?: ""
                    }
                }
                .addOnFailureListener {
                    showFailurePopup = true
                }
        }
    }

    // Fungsi untuk menyimpan perubahan
    fun saveProfileChanges() {
        currentUser?.let { user ->
            // Update Firebase Authentication profile
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build()

            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Update Firestore
                        val userData = mapOf("phoneNumber" to phoneNumber)
                        db.collection("users").document(user.uid)
                            .set(userData, SetOptions.merge()) // Gunakan merge untuk hanya mengubah atribut tertentu
                            .addOnSuccessListener {
                                showSuccessPopup = true
                            }
                            .addOnFailureListener {
                                showFailurePopup = true
                            }
                    }
                }
        }
    }

    // Memuat data saat layar dibuka
    LaunchedEffect(Unit) {
        loadUserData()
    }


    Scaffold(
        topBar = {
            TopAppBar(navController = navController,title="Profil")
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (profileImageUrl != null) {
                        AsyncImage(
                            model = profileImageUrl.toString(),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        androidx.compose.material3.Text(
                            text = "Keenan Tee",
                            style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.titleLarge.fontSize),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5A5AD6)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = {
                        Text(
                            text = "Nama Lengkap",
                            style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                            color = Blue500
                        )
                    },
                    textStyle = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Blue400,
                        unfocusedBorderColor = Blue200,
                        focusedLabelColor = Blue400,
                        unfocusedLabelColor = Blue200
                    )
                )


                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = currentUser?.email ?: "",
                    onValueChange = {  },
                    label = {
                        Text(
                            text = "Email",
                            style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                            color = Blue500
                        )
                    },
                    textStyle = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Blue400,
                        unfocusedBorderColor = Blue200,
                        focusedLabelColor = Blue400,
                        unfocusedLabelColor = Blue200
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = {
                        Text(
                            text = "Nomor Telepon",
                            style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                            color = Blue500
                        )
                    },
                    textStyle = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Blue400,
                        unfocusedBorderColor = Blue200,
                        focusedLabelColor = Blue400,
                        unfocusedLabelColor = Blue200
                    )
                )

                Spacer(modifier = Modifier.height(200.dp))

                NannyButton(
                    text = "Ubah",
                    onClick = { saveProfileChanges() },
                    containerColor = Blue500,
                    contentColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                if (showSuccessPopup) {
                    CustomPopup(
                        title = "Sukses!",
                        description = "Perubahan berhasil disimpan.",
                        imageResource = R.drawable.success, // Gambar sukses
                        onDismiss = { showSuccessPopup = false },
                        navController = navController
                    )
                }

                // Popup Gagal
                if (showFailurePopup) {
                    CustomPopup(
                        title = "Gagal",
                        description = "Terjadi kesalahan saat menyimpan.",
                        imageResource = R.drawable.success, // Gambar error
                        onDismiss = { showFailurePopup = false },
                        navController = navController
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                NannyButton(
                    text = "Hapus Akun",
                    onClick = { showDeleteAccountPopup = true },
                    containerColor = Blue500,
                    contentColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                if (showDeleteAccountPopup) {
                    CustomPopup(
                        navController = navController,
                        title = "Konfirmasi Hapus Akun",
                        description = "Apakah Anda yakin ingin menghapus akun? Tindakan ini tidak dapat dibatalkan.",
                        imageResource = R.drawable.warning,
                        onDismiss = { showDeleteAccountPopup = false },
                        primaryAction = {
                            FirebaseAuth.getInstance().currentUser?.delete()?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showSuccessPopup = true
                                } else {
                                    showFailurePopup = true
                                }
                            }
                        },
                        secondaryAction = {
                            showDeleteAccountPopup = false
                        }
                    )
                }


                if (showSuccessPopup) {
                    CustomPopup(
                        title = "Sukses!",
                        description = "Akun Anda telah berhasil dihapus.",
                        imageResource = R.drawable.success,
                        onDismiss = {
                            showSuccessPopup = false
                            navController.navigate("LoginScreen")
                        },
                        navController = navController
                    )
                }

                if (showFailurePopup) {
                    CustomPopup(
                        title = "Gagal",
                        description = "Terjadi kesalahan saat menghapus akun. Silakan coba lagi.",
                        imageResource = R.drawable.error,
                        onDismiss = { showFailurePopup = false },
                        navController = navController
                    )
                }
            }
        }
    )
}
@Composable
fun CustomPopup(
    navController: NavController,
    title: String,
    description: String,
    imageResource: Int,
    onDismiss: () -> Unit,
    primaryAction: (() -> Unit)? = null, // Fungsi opsional untuk tombol utama
    secondaryAction: (() -> Unit)? = null // Fungsi opsional untuk tombol sekunder
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            primaryAction?.let {
                Button(onClick = {
                    it()
                    onDismiss() // Tutup dialog setelah aksi
                }) {
                    Text("Ya", fontFamily = Fredoka)
                }
            }
        },
        dismissButton = {
            secondaryAction?.let {
                Button(onClick = {
                    it()
                    onDismiss() // Tutup dialog setelah aksi
                }) {
                    Text("Tidak", fontFamily = Fredoka)
                }
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Medium, fontFamily = Fredoka)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = description, fontSize = 16.sp, fontFamily = Fredoka)
            }
        },
        shape = RoundedCornerShape(16.dp),
    )
}