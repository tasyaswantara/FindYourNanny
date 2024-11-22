package com.example.projekpapb2.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projekpapb2.data.repository.AuthRepository
import com.example.projekpapb2.ui.theme.Fredoka

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, authRepository: AuthRepository) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Daftar Akun,",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF6667FF),
                    fontFamily = Fredoka,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Silakan isi untuk bergabung dengan kami!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontFamily = Fredoka,
                    fontWeight = FontWeight.Medium
                ),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Nama Lengkap Input
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Nama Lengkap", fontFamily = Fredoka) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6667FF),
                unfocusedBorderColor = Color.Gray,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontFamily = Fredoka) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6667FF),
                unfocusedBorderColor = Color.Gray,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nomor Telepon Input
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Nomor Telepon", fontFamily = Fredoka) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6667FF),
                unfocusedBorderColor = Color.Gray,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Kata Sandi", fontFamily = Fredoka) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6667FF),
                unfocusedBorderColor = Color.Gray,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Konfirmasi Kata Sandi", fontFamily = Fredoka) },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6667FF),
                unfocusedBorderColor = Color.Gray,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Daftar
        Button(
            onClick = {
                if (password == confirmPassword) {
                    authRepository.register(email, password, {
                        navController.navigate("login")
                    }, {
                        errorMessage = it
                    })
                } else {
                    errorMessage = "Password dan Konfirmasi Sandi tidak cocok"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6667FF)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Daftar", color = Color.White, fontFamily = Fredoka)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Redirect
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Sudah punya akun?",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                fontFamily = Fredoka
            )
            TextButton(onClick = { navController.navigate("login") }) {
                Text(
                    text = "Masuk",
                    color = Color(0xFF6667FF),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = Fredoka
                )
            }
        }

        // Pesan Error
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error, fontFamily = Fredoka)
        }
    }
}
