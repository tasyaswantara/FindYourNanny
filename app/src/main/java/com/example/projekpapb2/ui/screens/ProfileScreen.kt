package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.components.BottomNavbar
import com.example.projekpapb2.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun ProfileScreen(navController: NavController) {
    val currentUser = Firebase.auth.currentUser
    val profileImageUrl = currentUser?.photoUrl
    Scaffold(
        topBar = {
            com.example.projekpapb2.ui.components.TopAppBar(
                navController = navController,
                title = "Profil"
            )
        },
        bottomBar = { BottomNavbar(
            navController = navController,
            selectedScreen = "Profil",
            onItemSelected = { selected ->
                println("Selected screen: $selected")
            }
        ) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
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
                        contentDescription = "Default Profile Picture",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = currentUser?.displayName ?: "Nama Tidak Diketahui",
                        style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.titleLarge.fontSize),
                        color = Blue600
                    )
                    Text(
                        text = currentUser?.email ?: "Email Tidak Tersedia",
                        style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        color = Blue200
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            ProfileMenuItem(icon = R.drawable.ic_account, title = "Informasi Akun", "accountinfo", navController = navController)
            ProfileMenuItem(icon = R.drawable.ic_notification, title = "Notifikasi","notifikasi", navController = navController)
            ProfileMenuItem(icon = R.drawable.ic_logout, title = "Keluar Akun",null, navController = navController,onClick = {
                Firebase.auth.signOut() // Logout dari Firebase
                navController.navigate("login") { // Navigasi ke layar login
                    popUpTo(0)}


                })
        }
    }
}

@Composable
fun ProfileMenuItem(icon: Int, title: String, navigate: String?, navController: NavController, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick?.invoke() ?: navigate?.let {
                    navController.navigate(it)
                }
            }
            .padding(vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Blue600,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = Grey
        )
    }
}

@Composable
fun PopupLogout(
    navController: NavController,
    title: String,
    description: String,
    imageResource: Int,
    onDismiss: () -> Unit,

    ) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {

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
                Text(
                    text = "Keluar Akun",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Fredoka,
                    color = Blue600
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Apakah Anda yakin untuk keluar?",
                    fontSize = 16.sp,
                    fontFamily = Fredoka,
                    color = Blue600
                )
                Spacer(modifier = Modifier.height(12.dp))
                androidx.compose.material.Button(onClick = {
                    onDismiss()
                    navController.navigate("profil")
                }) {
                    androidx.compose.material.Text("Tidak", fontFamily = Fredoka)
                }
            }
        },
        shape = RoundedCornerShape(16.dp),

        )
}