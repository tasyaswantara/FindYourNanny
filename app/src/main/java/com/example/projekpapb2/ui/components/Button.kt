package com.example.projekpapb2.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projekpapb2.ui.theme.Blue500
import com.example.projekpapb2.ui.theme.Fredoka
import com.example.projekpapb2.ui.theme.ProjekPAPB2Theme

@Composable
fun NannyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Blue500,
    contentColor: Color = Color.White
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = Fredoka,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewButton() {
    ProjekPAPB2Theme {
        NannyButton(text = "Custom Colors",
            onClick = { /* Simulated click */ },
            containerColor = Blue500,
            contentColor = Color.White,
            modifier = Modifier.padding(16.dp))
    }
}