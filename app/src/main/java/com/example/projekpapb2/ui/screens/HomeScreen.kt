package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.components.NannyItem

@Composable
fun HomeScreen(navController: NavController, repository: NannyRepository) {
    val context = LocalContext.current
    var nannies by remember { mutableStateOf(listOf<Nanny>()) }

    LaunchedEffect(Unit) {
        nannies = repository.getNannies()
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(nannies) { nanny ->
            NannyItem(nanny = nanny, onClick = {
                navController.navigate("detail/${nanny.id}")
            })
        }
    }
}