package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.layout.Column
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
import com.nextsolutions.keyysafe.main.domain.model.CircleBarResult
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun CircleBarComponent(
    title: String,
    subTitle: String,
    data: CircleBarResult,
    isSizeAnimPlayed: Boolean
) {

    val windowInfo = rememberWindowInfo()

    Column(
        modifier = if (windowInfo.isTablet) {
            Modifier
                .width(width = (windowInfo.screenWidth / 2))
        } else {
            Modifier.fillMaxWidth()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

        Column(
            if (windowInfo.isTablet) Modifier else Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = KeySafeTheme.spaces.mediumLarge),
                text = title,
                color = KeySafeTheme.colors.text,
                textAlign = TextAlign.Start,
                fontSize = 25.sp
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = KeySafeTheme.spaces.mediumLarge),
                text = subTitle,
                color = Gray,
                textAlign = TextAlign.Start,
            )
        }



        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

        CirclePercentageBar(
            data = data,
            animationPlayed = isSizeAnimPlayed
        )

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

        Text(
            text = stringResource(R.string.total_entries_with_concatination, data.totalEntries.toString()),
            color = KeySafeTheme.colors.text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))


    }



}