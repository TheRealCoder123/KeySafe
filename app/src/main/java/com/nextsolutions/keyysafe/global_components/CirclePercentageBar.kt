package com.nextsolutions.keyysafe.global_components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.main.domain.model.CircleBarResult
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.Red

@Composable
fun CirclePercentageBar(
    data: CircleBarResult,
    radiusOuter: Dp = 90.dp,
    animDuration: Int = 1000,
    animationPlayed: Boolean
) {


    var isReadySweepAngleToAnimate by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = data.percentage){
        isReadySweepAngleToAnimate = true

    }

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = "size_anim"
    )

    val animateSweepAngle by animateFloatAsState(
        targetValue = if (isReadySweepAngleToAnimate) 360f * data.percentage / 100 else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = "sweep_angle_anim"
    )




    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
            ) {
                drawArc(
                    color = Gray,
                    startAngle = 90f,
                    sweepAngle = 360f,
                    useCenter = true,
                )
                drawArc(
                    color = if (data.showColorsCloseToEnd) {
                        when (data.percentage.toInt()) {
                            in 80..89 -> Orange
                            in 90..100 -> Red
                            else -> Green
                        }
                    } else {
                        Green
                    },
                    startAngle = 360f,
                    sweepAngle = animateSweepAngle,
                    useCenter = true,
                )
            }
            Box(modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .background(KeySafeTheme.colors.background)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 30.sp, color = Green)) {
                        append(if (data.dontCapPercentagesToInts) String.format("%.2f", data.percentage) else "${data.percentage.toInt()}")
                    }

                    withStyle(style = SpanStyle(fontSize = 16.sp, color = KeySafeTheme.colors.text)) {
                        append("%")
                    }
                },
                color = KeySafeTheme.colors.text
            )
        }



    }

}

