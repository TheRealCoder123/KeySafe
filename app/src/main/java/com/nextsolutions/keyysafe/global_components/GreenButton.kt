package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun GreenButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(0.dp),
    label: String
) {
    Button(
        modifier = modifier.height(50.dp),
        onClick = { onClick() },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = KeySafeTheme.colors.primary,
            contentColor = Color.White
        )
    ) {
        Text(
            text = label,
        )
    }
}

@Preview
@Composable
fun GreenButtonPreview() {
    GreenButton(onClick = { }, label = "Setup Biometrics")
}