package com.nextsolutions.keyysafe.global_components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.Red

@OptIn(ExperimentalTextApi::class)
@Composable
fun PieChart(
    data: List<Double>,
    radiusOuter: Dp = 90.dp,
    animDuration: Int = 1000,
    animationPlayed: Boolean
) {

    val context = LocalContext.current

    val totalSum = data.sum()
    val floatValue = mutableListOf<Float>()


    data.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = listOf(
        Green,
        Orange,
        Red
    )


    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = "Size_anim"
    )
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = "Rotation_anim"
    )



    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
               if (floatValue.isNotEmpty()){
                   floatValue.forEachIndexed { index, value ->
                       drawArc(
                           color = colors[index],
                           lastValue,
                           value,
                           useCenter = true,
                       )
                       lastValue += value
                   }
               }else{
                   drawArc(
                       color = Color.Black,
                       lastValue,
                       360f,
                       useCenter = true,
                   )
               }
            }
            Box(modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(KeySafeTheme.colors.background)
            )
        }



    }

}

