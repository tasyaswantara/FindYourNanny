package com.example.projekpapb2.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.Fredoka

@Composable
fun TopAppBar(
    navController: NavController,
    title: String
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
        colors =  CardDefaults.cardColors(containerColor = Blue600)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = {
                    navController.popBackStack()
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
                text = title,
                color = Color.White,
                style = TextStyle(fontFamily = Fredoka, fontSize = MaterialTheme.typography.titleLarge.fontSize),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
            )
        }
    }
}