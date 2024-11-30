package com.example.projekpapb2.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.projekpapb2.data.model.Nanny
import com.example.projekpapb2.data.repository.NannyRepository
import com.example.projekpapb2.ui.components.NannyItem
import com.example.projekpapb2.ui.theme.Blue600
import com.example.projekpapb2.ui.theme.ProjekPAPB2Theme

//@Composable
//fun HomeScreen(navController: NavController, repository: NannyRepository) {
//    val context = LocalContext.current
//    var nannies by remember { mutableStateOf(listOf<Nanny>()) }
//
//    LaunchedEffect(Unit) {
//        nannies = repository.getNannies()
//    }
//
//    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        items(nannies) { nanny ->
//            NannyItem(nanny = nanny, onClick = {
//                navController.navigate("detail/${nanny.id}")
//            })
//        }
//    }
//}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, repository: NannyRepository) {
    var currentReviewIndex by remember { mutableStateOf(0) }


    val menuItems = listOf(
        Pair("Bayi", R.drawable.baby_icon),
        Pair("Lansia", R.drawable.elderly_icon),
        Pair("Hewan", R.drawable.animal_icon),
        Pair("Rumah", R.drawable.home_icon)
    )
//    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavbar(
                navController = navController,
                selectedScreen = "Beranda",
                onItemSelected = { selected ->
                    println("Selected screen: $selected")
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 34.dp)

        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Selamat datang di",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium, fontFamily = Fredoka),
                color = Color.Black
            )
            Text(
                text = "Find Your Nanny",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = Fredoka),
                color = Blue600
            )
            Spacer(modifier = Modifier.height(24.dp))


            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 20.dp,
                backgroundColor = Blue600,
                modifier = Modifier.fillMaxWidth()
                    .size(width = 0.dp, height = 170.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 0.dp, bottom = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Hi,",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Fredoka
                            ),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp)) // Jarak antar teks
                        Text(
                            text = "Keenan Tee",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
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
                            .offset(x = (-8).dp, y = (-0).dp),
                        alignment = Alignment.BottomEnd
                    )

                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Menu Informasi",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = Fredoka)
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(22.dp), modifier = Modifier.fillMaxWidth()) {
                items(menuItems) { (title, iconResId) ->
                    MenuItem(
                        title = title,
                        iconResId = iconResId,
                        onClick = {
                            when (title) {
                                "Bayi" -> navController.navigate("babyList")
                                "Lansia" -> navController.navigate("elderlyList")
                                "Hewan" -> navController.navigate("animalList")
                                "Rumah" -> navController.navigate("homeList")
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Ulasan",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = Fredoka)
            )
            Spacer(modifier = Modifier.height(12.dp))

            ReviewCarousel()

            Spacer(modifier = Modifier.height(24.dp))


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
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Fredoka)
                )
            }

        }
    }


}

@Composable
fun MenuItem(title: String, iconResId: Int, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        modifier = Modifier
            .size(68.dp, 100.dp)
            .border(width = 1.dp, color = Blue600, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)  // Menambahkan klik pada Card
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "$title icon",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Fredoka
                )
            )
        }
    }
}


@Composable
fun ReviewCarousel() {
    val reviews = listOf(
        "Nanny Alina" to "Tepat waktu, sangat telaten dan bersih",
        "Nanny Zahra" to "Ramah, sabar, dan profesional",
        "Nanny Cinta" to "Pekerja keras dan detail",
        "Nanny Putri" to "Dapat menjaga anak dengan baik",
        "Nanny Bunga" to "Berpengalaman, sigap, dan jujur"
    )
    var currentReviewIndex by remember { mutableStateOf(0) }
    var clickCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Blue600,
                    shape = RoundedCornerShape(16.dp)
                )
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 0) {
                            // Geser ke kiri
                            if (currentReviewIndex > 0) {
                                currentReviewIndex -= 1
                            }
                        } else {
                            // Geser ke kanan
                            if (currentReviewIndex < reviews.size - 1) {
                                currentReviewIndex += 1
                            }
                        }
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Image(
                    painter = painterResource(id = R.drawable.review_placeholder),
                    contentDescription = "Nanny Review",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(end = 10.dp)
                        .align(Alignment.Bottom)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = reviews[currentReviewIndex].first,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Fredoka
                        ),
                        color = Blue600
                    )
                    Text(
                        text = reviews[currentReviewIndex].second,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = Fredoka
                        )
                    )
                }

                }
            }
        }

    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        reviews.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .padding(horizontal = 1.dp)
                    .background(
                        color = if (index == currentReviewIndex) Blue600 else Color.Gray,
                        shape = CircleShape
                    )
            )
        }
    }

}



@Composable
@Preview(showBackground = true)
fun PreviewHome(){
    ProjekPAPB2Theme {
//
//        HomeContent()
    }

}