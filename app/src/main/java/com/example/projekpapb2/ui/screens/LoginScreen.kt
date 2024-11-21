package com.example.projekpapb2.ui.screens

import android.app.Instrumentation
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projekpapb2.R
import com.example.projekpapb2.data.repository.AuthRepository
import com.example.projekpapb2.ui.theme.Fredoka
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authRepository: AuthRepository) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val firebaseAuth = FirebaseAuth.getInstance()

    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    val launcher = authLauncher(
        onAuthComplete = {result ->
            user =result.user
        },
        onAuthError = {
            user = null
        }
    )
//    val token = stringResource(R.string.web_id)
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Selamat datang
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Selamat datang,",

                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF6667FF),
                    fontFamily = Fredoka,
                    fontWeight = FontWeight.Bold

                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Silakan masuk untuk melanjutkan!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontFamily = Fredoka,
                    fontWeight = FontWeight.Medium
                ),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false // Reset error ketika input berubah
            },

            label = { Text("Email", fontFamily = Fredoka) },
            isError = emailError,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6667FF),
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )
        if (emailError) {
            Text(
                text = "Email tidak valid",
                color = Color.Red,
                fontFamily = Fredoka,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false // Reset error ketika input berubah
            },
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
            isError = passwordError, // Tampilkan warna merah jika error
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6667FF),
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )
        if (passwordError) {
            Text(
                text = "Password tidak boleh kosong",
                color = Color.Red,
                fontFamily = Fredoka,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        // Lupa Kata Sandi
        Text(
            text = "Lupa kata sandi?",
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp),
            color = Color(0xFF6667FF),
            fontFamily = Fredoka,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(32.dp))
        // Tombol Masuk
        Button(
            onClick = {
                if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = true
                }
                if (password.isBlank()) {
                    passwordError = true
                }
                if (!emailError && !passwordError) {
                    authRepository.login(email, password, {
                        navController.navigate("home")
                    }, {
                        errorMessage = it
                    })
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6667FF))
        ) {
            Text(text = "Masuk", color = Color.White,fontFamily = Fredoka)
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Atau Divider
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
            Text(
                text = "atau",
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,fontFamily = Fredoka
                )
            )
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Tombol Masuk dengan Google
        Button(
            onClick = {
//                val gso =
//                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(token)
//                        .requestEmail()
//                        .build()
//                val gsc = GoogleSignIn.getClient(context, gso)
//                launcher.launch(gsc.signInIntent)

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "Google Logo",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Masuk dengan Google", color = Color.Black,fontFamily = Fredoka)
        }

        Spacer(modifier = Modifier.height(24.dp))
        // Daftar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Belum punya Akun?",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                fontFamily = Fredoka
            )
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    text = "Daftar",
                    color = Color(0xFF6667FF),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = Fredoka
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error,fontFamily = Fredoka)
        }
    }
}
@Composable
fun authLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try{
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!,null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        }catch (e: ApiException){
            onAuthError(e)
        }
    }
}