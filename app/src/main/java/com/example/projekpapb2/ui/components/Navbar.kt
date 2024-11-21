package com.example.projekpapb2.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.theme.Fredoka
import com.example.projekpapb2.ui.theme.ProjekPAPB2Theme
import com.example.projekpapb2.ui.theme.Blue100

@Composable
fun BottomNavbar(
    navController: NavController,
    selectedScreen: String,
    onItemSelected: (String) -> Unit
) {
    BottomNavigation(
        backgroundColor = Blue100,
        contentColor = Color.Black
    ) {

        BottomNavigationItem(
            selected = selectedScreen == "Beranda",
            onClick = { onItemSelected("Beranda");
                navController.navigate("home") },
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (selectedScreen == "Beranda") R.drawable.icon_home_full else R.drawable.icon_home
                    ),
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = "Beranda",
                    style = TextStyle(
                        fontFamily = Fredoka,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        )


        BottomNavigationItem(
            selected = selectedScreen == "Riwayat",
            onClick = { onItemSelected("Riwayat") },
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (selectedScreen == "Riwayat") R.drawable.icon_history else R.drawable.icon_history
                    ),
                    contentDescription = "History"
                )
            },
            label = {
                Text(
                    text = "Riwayat",
                    style = TextStyle(
                        fontFamily = Fredoka,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        )


        BottomNavigationItem(
            selected = selectedScreen == "Profil",
            onClick = {
                onItemSelected("Profil");
                navController.navigate("profil")
            },
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (selectedScreen == "Profil") R.drawable.icon_profile_full else R.drawable.icon_profile
                    ),
                    contentDescription = "Profile"
                )
            },
            label = {
                Text(
                    text = "Profil",
                    style = TextStyle(
                        fontFamily = Fredoka,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavbar() {
    ProjekPAPB2Theme {
//        BottomNavbar(
//            navController = navController,
//            selectedScreen = "Beranda",
//            onItemSelected = { selected ->
//                println("Selected screen: $selected")
//            }
//        )
    }
}

