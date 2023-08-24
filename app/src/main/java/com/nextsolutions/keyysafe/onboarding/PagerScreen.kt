package com.nextsolutions.keyysafe.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {


    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(onBoardingPage.animation))




}