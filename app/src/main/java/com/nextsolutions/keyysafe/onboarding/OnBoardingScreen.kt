package com.nextsolutions.keyysafe.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController
) {


    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        HorizontalPager(
            modifier = Modifier
                .weight(10f),
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) {
            PagerScreen(onBoardingPage = pages[it])
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            pagerState = pagerState,
            activeColor = KeySafeTheme.colors.primary,
            inactiveColor = Gray
        )

        if (pagerState.currentPage != 2){
            Box(modifier = Modifier.fillMaxWidth()
                .height(50.dp)
                .padding(KeySafeTheme.spaces.mediumLarge)
                .weight(1f)
            )
        }

        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            visible = pagerState.currentPage == 2,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ){
            Box(modifier = Modifier.fillMaxWidth()
                .padding(KeySafeTheme.spaces.mediumLarge)
            ){
                GreenButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(MainNavigation.Dashboard.route)
                    },
                    label = stringResource(R.string.continue_to_the_app)
                )
            }
        }



    }




}