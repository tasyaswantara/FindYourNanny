package com.example.projekpapb2.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.theme.Blue200
import com.example.projekpapb2.ui.theme.Blue400
import com.example.projekpapb2.ui.theme.Blue500
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInformationScreen() {
    var text by remember { mutableStateOf("Keenan Tee") }

    Scaffold(
        topBar = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
                colors =  CardDefaults.cardColors(containerColor = Blue600)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    IconButton(
                        onClick = {
                            // Aksi tombol kembali
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    androidx.compose.material3.Text(
                        text = "Profil",
                        color = Color.White,
                        style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.titleLarge.fontSize),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                    )
                }
            }
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
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
                    value = text,
                    onValueChange = { text = it },
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
                    value = "keenantee@gmail.com",
                    onValueChange = { text = it },
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
                    value = "085870180275",
                    onValueChange = { text = it },
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
            }
        }
    )
}