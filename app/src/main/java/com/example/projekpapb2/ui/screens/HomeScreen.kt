package com.example.projekpapb2.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.components.BottomNavbar
import com.example.projekpapb2.ui.theme.Blue100
import com.example.projekpapb2.ui.theme.Fredoka
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.components.NannyItem
import com.example.projekpapb2.ui.theme.Blue500
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.ProjekPAPB2Theme

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


@Composable
fun HomeContent() {
    var currentReviewIndex by remember { mutableStateOf(0) } // State untuk ulasan aktif
    val reviews = listOf(
        Pair("Nanny Rani", "Tepat waktu, sangat telaten dan bersih"),
        Pair("Nanny Siti", "Ramah, sabar, dan profesional"),
        Pair("Nanny Anita", "Berpengalaman, sigap, dan jujur")
    )
    // Deklarasi data menuItems
    val menuItems = listOf("Bayi", "Lansia", "Hewan", "Rumah")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 34.dp)

    ) {
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "Selamat datang di",
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, fontFamily = Fredoka),
            color = Color.Black
        )
        Text(
            text = "Find Your Nanny",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Fredoka),
            color = Blue600
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Card
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 20.dp,
            backgroundColor = Blue600,
            modifier = Modifier
                .size(width = 306.dp, height = 170.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp, end = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Keenan Tee",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fredoka
                        ),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Jarak antar teks
                    Text(
                        text = "Kenan Tee artis terkenal",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Fredoka
                        ),
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .offset(x = (-8).dp, y = (-8).dp),
                    alignment = Alignment.BottomEnd
                )

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu Informasi
        Text(
            text = "Menu Informasi",
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Fredoka)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(menuItems) { title ->
                MenuItem(title)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Review Section
        Text(
            text = "Ulasan",
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Fredoka)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.review_placeholder),
                    contentDescription = "Nanny Review",
                    modifier = Modifier
                        .size(80.dp) // Ukuran gambar ulasan
                        .padding(end = 16.dp)
                        .align(Alignment.Bottom)
                )
                Column(
                    modifier = Modifier.weight(1f) // Membuat teks responsif
                ) {
                    Text(
                        text = reviews[currentReviewIndex].first,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Fredoka)
                    )
                    Text(
                        text = reviews[currentReviewIndex].second,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light, fontFamily = Fredoka),
                        color = Color.Gray
                    )
                }
                IconButton(
                    onClick = {
                        currentReviewIndex = (currentReviewIndex + 1) % reviews.size
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next), // Ikon untuk next
                        contentDescription = "Next Review",
                        tint = Blue100
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contact Button
        Button(
            onClick = { /* Handle Contact Action */ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Blue100,
                contentColor = Blue600

            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(color = Blue600, width = 1.dp)
        ) {
            Text(
                text = "Hubungi Kami",
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Fredoka)
            )
        }

    }
    BottomNavbar(
        selectedScreen = "Beranda",
        onItemSelected = { selected ->
            println("Selected screen: $selected")
        }
    )

}

@Composable
fun MenuItem(title: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        modifier = Modifier
            .size(100.dp) // Ukuran item menu
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}




@Composable
@Preview(showBackground = true)
fun PreviewHome(){
    ProjekPAPB2Theme {

        HomeContent()
    }

}