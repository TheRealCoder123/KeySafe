package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun GreenButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(0.dp),
    label: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = KeySafeTheme.colors.primary,
        contentColor = Color.White,
        disabledBackgroundColor = KeySafeTheme.colors.searchTFBackground
    ),
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    isEnabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        modifier = modifier.height(50.dp),
        onClick = { onClick() },
        shape = shape,
        colors = colors,
        elevation = elevation,
        enabled = isEnabled
    ) {

        if (icon != null){

            Icon(
                imageVector = icon,
                contentDescription = label
            )

            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.medium))

        }

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