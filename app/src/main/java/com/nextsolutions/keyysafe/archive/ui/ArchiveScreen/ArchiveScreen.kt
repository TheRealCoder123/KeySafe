package com.nextsolutions.keyysafe.archive.ui.ArchiveScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.common.util.rememberWindowInfo
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.CircleBarComponent
import com.nextsolutions.keyysafe.global_components.PieChartComponent
import com.nextsolutions.keyysafe.global_components.WarningComponent
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun ArchiveScreen(
    navController: NavController,
    viewModel: ArchiveViewModel = hiltViewModel(),
) {

    val windowInfo = rememberWindowInfo()

    LaunchedEffect(key1 = Unit) {
        viewModel.isPieChartAnimPlayed = true
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.isCirclePercentageComponentAnimPlayed = true
    }


    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(id = R.string.archive),
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            item {
                WarningComponent(data = viewModel.warningData)
            }


            if (windowInfo.isTablet){

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {

                        PieChartComponent(
                            pieChartState = viewModel.pieChartData,
                            isPieChartAnimPlayed = viewModel.isPieChartAnimPlayed,
                            title = stringResource(R.string.archived_password_analysis),
                            subTitle = stringResource(R.string.brief_analysis_of_how_many_passwords_are_weak_medium_and_strong_in_your_archive)
                        )


                        CircleBarComponent(
                            title = stringResource(R.string.archived_entries),
                            subTitle = stringResource(R.string.displaying_how_many_percentages_of_your_entries_are_archived),
                            data = viewModel.circleBarData,
                            isSizeAnimPlayed = viewModel.isCirclePercentageComponentAnimPlayed
                        )

                    }
                }

            }else{
                item {
                    PieChartComponent(
                        modifier = Modifier.fillMaxWidth(),
                        pieChartState = viewModel.pieChartData,
                        isPieChartAnimPlayed = viewModel.isPieChartAnimPlayed,
                        title = stringResource(R.string.archived_password_analysis),
                        subTitle = stringResource(R.string.brief_analysis_of_how_many_passwords_are_weak_medium_and_strong_in_your_archive)
                    )
                }

                item {
                    CircleBarComponent(
                        title = stringResource(R.string.archived_entries),
                        subTitle = stringResource(R.string.displaying_how_many_percentages_of_your_entries_are_archived),
                        data = viewModel.circleBarData,
                        isSizeAnimPlayed = viewModel.isCirclePercentageComponentAnimPlayed
                    )
                }
            }



           if (viewModel.entries.isNotEmpty()){
               item {
                   Text(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(
                               horizontal = KeySafeTheme.spaces.mediumLarge,
                               vertical = KeySafeTheme.spaces.medium
                           ),
                       text = stringResource(id = R.string.archived_entries),
                       color = KeySafeTheme.colors.text
                   )
               }

               items(viewModel.entries.sortedByDescending { it.timeStamp }) {entry ->
                   EntryItem(
                       entry = entry,
                       onEntryClick = { clickedEntry ->
                           navController.navigate(MainNavigation.CreateEntryScreen.passEntryId(clickedEntry.entryId))
                       }
                   )
                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(1.dp)
                       .background(Gray))
               }
           }


        }

    }





}