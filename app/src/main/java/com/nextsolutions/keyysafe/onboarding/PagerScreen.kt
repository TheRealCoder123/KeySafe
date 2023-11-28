package com.nextsolutions.keyysafe.onboarding

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {


    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(onBoardingPage.animation))


    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(KeySafeTheme.spaces.mediumLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        item {

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

            Text(
                text = stringResource(id = onBoardingPage.title),
                color = KeySafeTheme.colors.text,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.large))

            Text(
                text = stringResource(id = onBoardingPage.text),
                color = KeySafeTheme.colors.text,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.extraLarge))


            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(150.dp)
            )




        }

    }



}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PagerScreenPrev() {
    PagerScreen(onBoardingPage = OnBoardingPage.First)
}