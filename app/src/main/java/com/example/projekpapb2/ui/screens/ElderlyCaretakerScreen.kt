package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.navigation.NavRoutes
import com.example.projekpapb2.ui.components.NannyItem

@Composable
fun ElderlyCaretakerScreen(navController: NavController, repository: NannyRepository) {
    var caretakers by remember { mutableStateOf(emptyList<Nanny>()) }

    LaunchedEffect(Unit) {
        caretakers = repository.getNannies()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(caretakers) { caretaker ->
            NannyItem(
                nanny = caretaker,
                onClick = {
                    navController.navigate(NavRoutes.detailRoute(caretaker.id))
                }
            )
        }
    }
}
