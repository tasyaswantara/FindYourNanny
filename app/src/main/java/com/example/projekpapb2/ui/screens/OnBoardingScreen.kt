package com.example.projekpapb2.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projekpapb2.R
import com.example.projekpapb2.ui.theme.Blue100
import com.example.projekpapb2.ui.theme.Blue500
import com.example.projekpapb2.ui.theme.Fredoka
import com.example.projekpapb2.ui.theme.ProjekPAPB2Theme


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: OnBoardingInterface,
    navController: NavController
){
    var currentPage by remember { mutableIntStateOf(0) }
    var page: OnBoardingInterface = pages[currentPage]
    var isLastPage: Boolean = currentPage == pages.lastIndex
    val onNextClick = {
        if (currentPage < pages.lastIndex) {
            currentPage += 1
        } else {
            navController.navigate("login")
        }
    }
    val onSkipClick = {
        navController.navigate("login")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        // Image
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = page.title,
            fontSize = 20.sp,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = Blue500,
            fontFamily = Fredoka,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = page.description,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontFamily = Fredoka,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

        if (isLastPage) {
            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Lanjut", fontFamily = Fredoka, color = Color(0xFF6667FF),
                    style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onNextClick,
                    modifier = Modifier.fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue500 ,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Lanjut", fontFamily = Fredoka, style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = onSkipClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(1.dp, Color(0xFF6667FF), RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue100),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Lewati", fontFamily = Fredoka, color = Blue500, style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }


    }



