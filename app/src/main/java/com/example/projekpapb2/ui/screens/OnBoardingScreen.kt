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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projekpapb2.ui.theme.Blue100
import com.example.projekpapb2.ui.theme.Blue500
import com.example.projekpapb2.ui.theme.Fredoka


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
        Spacer(modifier = Modifier.height(65.dp))

        // Image
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = page.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Blue500,
            fontFamily = Fredoka,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = page.description,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontFamily = Fredoka,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

        if (isLastPage) {
            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Blue500 ,
                contentColor = Color.White
            )
            ) {
                Text(text = "Lanjut", fontFamily = Fredoka, style = MaterialTheme.typography.bodyMedium, fontSize = 14.sp)
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
                    Text(text = "Lanjut", fontFamily = Fredoka, style = MaterialTheme.typography.bodyMedium, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = onSkipClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(1.dp, Color(0xFF6667FF), RoundedCornerShape(30.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue100),
                    shape = RoundedCornerShape(30.dp),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp) // Sesuaikan padding
                ) {
                    Text(
                        text = "Lewati",
                        fontFamily = Fredoka,
                        color = Blue500,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }


    }



