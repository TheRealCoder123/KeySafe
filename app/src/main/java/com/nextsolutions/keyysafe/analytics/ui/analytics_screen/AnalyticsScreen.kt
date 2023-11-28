package com.nextsolutions.keyysafe.analytics.ui.analytics_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.analytics.ui.analytics_screen.components.AllEntriesBottomSheet
import com.nextsolutions.keyysafe.analytics.ui.brute_force_resistance_analytic_screen.BruteForceResistance
import com.nextsolutions.keyysafe.analytics.ui.password_age_meter_anlytics_screen.PasswordAgeMeter
import com.nextsolutions.keyysafe.analytics.ui.password_diversity_checker_analtyic_screen.PasswordDiversityChecker
import com.nextsolutions.keyysafe.analytics.ui.password_strength_analytics_screen.PasswordStrength
import com.nextsolutions.keyysafe.analytics.ui.unique_password_analytic_screen.UniquePassword
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerItem
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun AnalyticsScreen(
    navHostController: NavHostController,
    viewModel: AnalyticsScreenViewModel = hiltViewModel()
) {

    val viewState = viewModel.state.value

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            AllEntriesBottomSheet(
                entries = viewState.entries,
                onClick = {
                    viewModel.selectEntry(it.entryId)
                    scope.launch {
                        sheetState.hide()
                    }
                },
                onLongClick = {
                    navHostController.navigate(MainNavigation.CreateEntryScreen.passEntryId(it.entryId))
                }
            )
        },
        sheetBackgroundColor = KeySafeTheme.colors.dialogBgColor
    ){


        Scaffold(
            backgroundColor = KeySafeTheme.colors.background,
            topBar = {
                BackTopAppBar(
                    title = when(pagerState.currentPage){
                        0 -> stringResource(R.string.password_strength_checker)
                        1 -> stringResource(R.string.password_age_meter)
                        2 -> stringResource(R.string.password_diversity_checker)
                        3 -> stringResource(R.string.unique_password)
                        4 -> stringResource(R.string.brute_force_resistance)
                        else -> stringResource(R.string.analytics_screen)
                    },
                    onBackPress = {
                        navHostController.navigateUp()
                    }
                )
            }
        ) {

            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {

                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    item {
                        Box(modifier = Modifier.padding(KeySafeTheme.spaces.medium)){
                            DrawerItem(
                                icon = if (viewState.checkByEntry == AnalyticsScreenViewModel.NO_ENTRY_SELECTED) Icons.Default.ContentPasteSearch else Icons.Default.Close,
                                text = viewState.checkByEntry?.title ?: stringResource(id = R.string.by_entry),
                                isSelected = true,
                                onClick = {
                                    if (viewState.checkByEntry == AnalyticsScreenViewModel.NO_ENTRY_SELECTED){
                                        scope.launch {
                                            sheetState.show()
                                        }
                                    }else{
                                        viewModel.unSelectEntry()
                                    }
                                }
                            )
                        }

                        Box(modifier = Modifier.padding(KeySafeTheme.spaces.medium)){
                            DrawerItem(
                                icon = Icons.Default.ViewCarousel,
                                text = stringResource(id = R.string.overview),
                                isSelected = true,
                                onClick = {

                                }
                            )
                        }
                    }

                }

                HorizontalPager(
                    modifier = Modifier
                        .weight(10f),
                    count = 5,
                    state = pagerState,
                    verticalAlignment = Alignment.Top
                ) { pageId ->
                    when(pageId){
                        0 -> PasswordStrength()
                        1 -> PasswordAgeMeter()
                        2 -> PasswordDiversityChecker()
                        3 -> UniquePassword()
                        4 -> BruteForceResistance()
                    }
                }

                HorizontalPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1f),
                    pagerState = pagerState,
                    activeColor = KeySafeTheme.colors.primary,
                    inactiveColor = Gray,
                    indicatorHeight = 10.dp,
                    indicatorWidth = 10.dp
                )

            }

        }



    }




}