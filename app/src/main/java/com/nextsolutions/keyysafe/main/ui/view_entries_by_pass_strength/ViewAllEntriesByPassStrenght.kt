package com.nextsolutions.keyysafe.main.ui.view_entries_by_pass_strength

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.util.rememberLifecycleEvent
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.SearchTextField
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.Red

@Composable
fun ViewAllEntriesByPassStrength(
    navController: NavController,
    viewModel: ViewAllEntriesByPassStrengthViewModel = hiltViewModel(),
) {

    val systemUiController = rememberSystemUiController()
    val lifecycle = rememberLifecycleEvent()

    systemUiController.setStatusBarColor(viewModel.topBarsColor)


    LaunchedEffect(key1 = viewModel.searchTextFieldState){
        viewModel.search(viewModel.searchTextFieldState)
    }

    LaunchedEffect(key1 = lifecycle){
        if (lifecycle == Lifecycle.Event.ON_STOP){
            viewModel.topBarsColor = Green
        }else if(lifecycle == Lifecycle.Event.ON_RESUME){
            viewModel.topBarsColor = when(viewModel.byStrength){
                PasswordChecker.PasswordStrength.STRONG -> Green
                PasswordChecker.PasswordStrength.MEDIUM -> Orange
                PasswordChecker.PasswordStrength.WEAK -> Red
            }
        }
    }



    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {

            BackTopAppBar(
                title = when(viewModel.byStrength){
                    PasswordChecker.PasswordStrength.STRONG -> stringResource(R.string.all_strong_passwords)
                    PasswordChecker.PasswordStrength.MEDIUM -> stringResource(R.string.all_medium_passwords)
                    PasswordChecker.PasswordStrength.WEAK -> stringResource(R.string.all_weak_passwords)
                },
                onBackPress = {
                    navController.navigateUp()
                },
                backgroundColor = when(viewModel.byStrength){
                    PasswordChecker.PasswordStrength.STRONG -> Green
                    PasswordChecker.PasswordStrength.MEDIUM -> Orange
                    PasswordChecker.PasswordStrength.WEAK -> Red
                }
            )

            SearchTextField(
                value = viewModel.searchTextFieldState,
                onValueChange = { query -> viewModel.searchTextFieldState = query },
                onClearTextField = {
                    viewModel.searchTextFieldState = ""
                }
            )


        }

        if (viewModel.entries.isNotEmpty()){
            items(viewModel.entries.sortedByDescending { it.timeStamp }){entry ->
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
        }else{
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = KeySafeTheme.spaces.mediumLarge,
                            vertical = KeySafeTheme.spaces.veryLarge
                        ),
                    text = when(viewModel.byStrength){
                        PasswordChecker.PasswordStrength.STRONG -> stringResource(R.string.seems_like_you_do_not_have_any_strong_passwords)
                        PasswordChecker.PasswordStrength.MEDIUM -> stringResource(R.string.seems_like_you_dont_have_any_medium_passwords)
                        PasswordChecker.PasswordStrength.WEAK -> stringResource(R.string.good_job_seems_like_you_dont_have_any_weak_passwords_keep_it_that_way)
                    },
                    color = Orange,
                    textAlign = TextAlign.Center
                )
            }
        }

    }


}