package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange

@Composable
fun OutlinedOrangeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(0.dp),
    label: String,
    icon: ImageVector? = null
) {
    OutlinedButton(
        modifier = modifier
            .height(50.dp),
        onClick = { onClick() },
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = KeySafeTheme.colors.text,
        ),
        border = BorderStroke(1.dp, Orange)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = label,
                tint = KeySafeTheme.colors.iconTint
            )

            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))
        }

        Text(
            text = label,
            color = KeySafeTheme.colors.description,
        )
    }
}

@Preview
@Composable
fun OutlinedOrangeButtonPreview() {
    OutlinedOrangeButton(
        onClick = {},
        label = "FAQ"
    )
}