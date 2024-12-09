package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projekpapb2.ui.theme.Blue600
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import com.example.projekpapb2.ui.theme.Fredoka


@Composable
fun NotificationScreen(navController: NavController) {
    Scaffold(
        topBar = {
            com.example.projekpapb2.ui.components.TopAppBar(
                navController = navController,
                title = "Notifikasi"
            )
        }

    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Nyalakan notifikasi untuk memberikan informasi secara langsung!",
                fontFamily = Fredoka,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notifikasi",
                    fontFamily = Fredoka,
                    fontSize = 20.sp,
                    color = Blue600
                )
                var isChecked by remember { mutableStateOf(false) }
                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Blue600,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = Blue600,
                        uncheckedTrackColor = Blue600
                    )
                )
            }
        }
    }
}
