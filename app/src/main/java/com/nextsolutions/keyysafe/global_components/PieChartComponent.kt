package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.common.util.rememberWindowInfo
import com.nextsolutions.keyysafe.main.domain.model.PieChartResult
import com.nextsolutions.keyysafe.main.ui.dashboard.components.ChartColorDescription
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.Red

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PieChartComponent(
    modifier: Modifier = Modifier,
    pieChartState: PieChartResult,
    isPieChartAnimPlayed: Boolean,
    title: String,
    subTitle: String,
    onStrongClick: () -> Unit = {},
    onMediumClick: () -> Unit = {},
    onWeakClick: () -> Unit = {},
) {

    val windowInfo = rememberWindowInfo()

    Column(
        modifier = modifier.then(
            if (!windowInfo.isTablet){
                Modifier.fillMaxWidth()
            }else {
                Modifier
            }
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = KeySafeTheme.spaces.mediumLarge),
                text = title,
                color = KeySafeTheme.colors.text,
                textAlign = TextAlign.Start,
                fontSize = 25.sp
            )

            Text(
                modifier = if (windowInfo.isTablet) {
                    Modifier
                        .width(width = (windowInfo.screenWidth / 2))
                        .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
                } else {
                    Modifier.fillMaxWidth()
                        .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
                },
                text = subTitle,
                color = Gray,
                textAlign = TextAlign.Start,
            )
        }

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

        PieChart(
            data = pieChartState.pieChartData,
            animationPlayed = isPieChartAnimPlayed
        )
        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
        Text(
            text = if (pieChartState.pieChartData.isEmpty()) "Calculating" else "Total passwords: ${pieChartState.totalPasswords}",
            color = KeySafeTheme.colors.text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

        FlowRow(
            modifier = if (windowInfo.isTablet) {
                Modifier
                    .width(width = (windowInfo.screenWidth / 2))
                    .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
            } else {
                Modifier.fillMaxWidth()
                    .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
            },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ChartColorDescription(
                modifier = Modifier.clickable {
                    onStrongClick()
                },
                color = Green,
                text = "${pieChartState.strongPasswords} ${stringResource(R.string.strong)}"
            )
            ChartColorDescription(
                modifier = Modifier.clickable {
                    onMediumClick()
                },
                color = Orange,
                text = "${pieChartState.mediumPasswords} ${stringResource(R.string.medium)}"
            )
            ChartColorDescription(
                modifier = Modifier.clickable {
                    onWeakClick()
                },
                color = Red,
                text = "${pieChartState.weakPasswords} ${stringResource(R.string.weak)}"
            )
        }

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))
    }
}