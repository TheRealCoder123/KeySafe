package com.nextsolutions.keyysafe.trash.ui.TrashScreen

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
import com.nextsolutions.keyysafe.common.util.rememberWindowInfo
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.CircleBarComponent
import com.nextsolutions.keyysafe.global_components.WarningComponent
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.trash.ui.TrashScreen.components.TrashEntryDialog
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun TrashScreen(
    navController: NavController,
    viewModel: TrashScreenViewModel = hiltViewModel(),
) {

    val windowInfo = rememberWindowInfo()

    LaunchedEffect(key1 = Unit) {
        viewModel.isCirclePercentageComponentAnimPlayed = true
    }

    TrashEntryDialog(
        isVisible = viewModel.isTrashEntryDialogVisible,
        onDismiss = { viewModel.isTrashEntryDialogVisible = false },
        onDelete = {
            viewModel.deleteEntry()
            viewModel.isTrashEntryDialogVisible = false
        },
        onRemoveFromTrash = {
            viewModel.removeFromTrash()
            viewModel.isTrashEntryDialogVisible = false
        }
    )

    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(R.string.trash),
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }
    ){
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){

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

                        CircleBarComponent(
                            title = stringResource(R.string.trash_available_space),
                            subTitle = stringResource(R.string.maximum_trash_space_available_is_150_entries),
                            data = viewModel.storageCircleBarResult,
                            isSizeAnimPlayed = viewModel.isCirclePercentageComponentAnimPlayed
                        )

                        CircleBarComponent(
                            title = stringResource(R.string.moved_to_trash_entries),
                            subTitle = stringResource(R.string.all_entries_that_have_been_moved_to_trash_archived_not_included),
                            data = viewModel.trashedEntriesCircleBarResult,
                            isSizeAnimPlayed = viewModel.isCirclePercentageComponentAnimPlayed
                        )

                    }

                }

            }else{
                item {
                    CircleBarComponent(
                        title = stringResource(R.string.trash_available_space),
                        subTitle = stringResource(R.string.maximum_trash_space_available_is_150_entries),
                        data = viewModel.storageCircleBarResult,
                        isSizeAnimPlayed = viewModel.isCirclePercentageComponentAnimPlayed
                    )
                    CircleBarComponent(
                        title = stringResource(R.string.moved_to_trash_entries),
                        subTitle = stringResource(R.string.all_entries_that_have_been_moved_to_trash_archived_not_included),
                        data = viewModel.trashedEntriesCircleBarResult,
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
                        text = stringResource(R.string.entries_in_trash),
                        color = KeySafeTheme.colors.text
                    )
                }

                items(viewModel.entries.sortedByDescending { it.timeStamp }) {entry ->
                    EntryItem(
                        entry = entry,
                        onEntryClick = { clickedEntry ->
                            viewModel.isTrashEntryDialogVisible = true
                            viewModel.selectedEntryId = clickedEntry.entryId
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray)
                    )
                }
            }

        }
    }
}