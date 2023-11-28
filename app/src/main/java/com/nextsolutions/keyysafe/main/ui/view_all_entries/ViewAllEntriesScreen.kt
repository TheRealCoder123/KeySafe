package com.nextsolutions.keyysafe.main.ui.view_all_entries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.SearchTextField
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.ui.theme.Gray

@Composable
fun ViewAllEntriesScreen(
    navController: NavController,
    viewModel: ViewAllEntriesScreenViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = viewModel.searchTextFieldState){
        viewModel.search(viewModel.searchTextFieldState)
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {

            BackTopAppBar(
                title = "All Entries",
                onBackPress = {
                    navController.navigateUp()
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

    }



}