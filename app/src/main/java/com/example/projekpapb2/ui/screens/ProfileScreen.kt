package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.theme.*

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
                colors = CardDefaults.cardColors(containerColor = Blue600)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Profil",
                        color = Color.White,
                        style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.titleLarge.fontSize)
                    )
                }
            }
        },
        bottomBar = { BottomNavigationBar() }
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
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Keenan Tee",
                        style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.titleLarge.fontSize),
                        color = Blue600
                    )
                    Text(
                        text = "keenantee@gmail.com",
                        style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        color = Blue200
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            ProfileMenuItem(icon = R.drawable.ic_account, title = "Informasi Akun")
            ProfileMenuItem(icon = R.drawable.ic_notification, title = "Notifikasi")
            ProfileMenuItem(icon = R.drawable.ic_logout, title = "Keluar Akun")
        }
    }
}

@Composable
fun ProfileMenuItem(icon: Int, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
fun BottomNavigationBar() {
    val selectedIndex = remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Blue100,
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Beranda"
                )
            },
            label = { Text("Beranda", style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyMedium.fontSize)) },
            selected = selectedIndex.value == 0,
            onClick = { selectedIndex.value = 0 },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Blue300,
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "Riwayat"
                )
            },
            label = { Text("Riwayat", style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyMedium.fontSize)) },
            selected = selectedIndex.value == 1,
            onClick = { selectedIndex.value = 1 },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Blue300,
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profilenavbar),
                    contentDescription = "Profil"
                )
            },
            label = { Text("Profil", style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.bodyMedium.fontSize)) },
            selected = selectedIndex.value == 2,
            onClick = { selectedIndex.value = 2 },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Blue300,
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black
            )
        )
    }
}
