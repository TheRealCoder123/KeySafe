package com.nextsolutions.keyysafe.settings.ui.AutoFill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun StepsComponent(
    number: Int,
    annotatedString: AnnotatedString
) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Gray)
                .size(30.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = number.toString(),
                color = Color.Black,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

        Text(
            text = annotatedString,
            color = KeySafeTheme.colors.text,
        )


    }

}

@Preview
@Composable
fun StepsComponentPrev() {
    StepsComponent(number = 1, buildAnnotatedString {
        withStyle(
            style = SpanStyle()
        ){
            append("Open ")
        }
        withStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold)
        ){
            append("Autofill Service ")
        }
        withStyle(
            style = SpanStyle()
        ){
            append("in you device settings")
        }
    })
}