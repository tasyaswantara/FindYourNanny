package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.navigation.NavRoutes
import com.example.projekpapb2.ui.components.NannyItem
import com.example.projekpapb2.ui.components.TopAppBar
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElderlyCaretakerScreen(navController: NavController, repository: NannyRepository) {
    var nannies by remember { mutableStateOf(emptyList<Nanny>()) }
    var searchQuery by remember { mutableStateOf("") }
    var filteredNannies by remember { mutableStateOf(emptyList<Nanny>()) }

    LaunchedEffect(Unit) {
        nannies = repository.getNannies()
        filteredNannies = nannies
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(navController = navController, title = "Daftar Pengurus Lansia")
        Spacer(modifier = Modifier.height(20.dp))
        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                filteredNannies = if (query.isEmpty()) {
                    nannies
                } else {
                    nannies.filter { nanny ->
                        nanny.name.contains(query, ignoreCase = true)
                    }
                }
            },
            placeholder = {
                Text(text = "Cari pengurus lansia...", fontFamily = Fredoka)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .border(
                    width = 1.dp,
                    color = Blue600,
                    shape = RoundedCornerShape(50.dp)
                ),
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Content
        if (filteredNannies.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredNannies) { nanny ->
                    NannyItem(
                        nanny = nanny,
                        onClick = {
                            navController.navigate(NavRoutes.detailRoute(nanny.id))
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Tidak ada hasil pencarian",fontFamily = Fredoka)
            }
        }
    }
}