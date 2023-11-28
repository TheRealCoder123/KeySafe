package com.nextsolutions.keyysafe.onboarding

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.nextsolutions.keyysafe.R

sealed class OnBoardingPage(
    @RawRes
    val animation: Int,
    @StringRes
    val title: Int,
    @StringRes
    val text: Int
) {


    object First : OnBoardingPage(
        animation = R.raw.first_screen_anim,
        title = R.string.welcome_to_keysafe,
        text = R.string.first_screen_text
    )
    object Second : OnBoardingPage(
        animation = R.raw.second_screen_anim,
        title = R.string.your_digital_vault,
        text = R.string.second_screen_text
    )
    object Third : OnBoardingPage(
        animation = R.raw.third_screen_anim,
        title = R.string.your_security_matters,
        text = R.string.third_screen_text
    )

}
