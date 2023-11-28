package com.nextsolutions.keyysafe.settings.ui.ViewFileDataScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerItem
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.White
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewFileDataScreen(
    navHostController: NavHostController,
    viewModel: ViewFileDataViewModel = hiltViewModel()
) {

    val context = LocalContext.current


    LaunchedEffect(key1 = viewModel.isRestoreClicked){
        if (viewModel.isRestoreClicked){
            delay(2000)
            navHostController.navigateUp()
        }
    }



    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            TopAppBar(
                backgroundColor = KeySafeTheme.colors.primary,
                navigationIcon = {
                        IconButton(
                            onClick = {
                                navHostController.navigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = stringResource(R.string.back),
                                tint = White
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.restore_data),
                            color = White
                        )
                    },
                    actions = {
                        AnimatedVisibility(
                            visible = viewModel.restoreStateResponse.success,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
                                    .clickable {
                                        viewModel.restore()
                                    },
                                text = stringResource(R.string.restore),
                                color = White
                            )
                        }
                    }
                )
            }


        ) {


            AnimatedVisibility(
                visible = viewModel.isRestoreClicked,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = stringResource(id = R.string.done),
                        tint = KeySafeTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Text(
                        text = "Successfully restored all entries and labels",
                        color = KeySafeTheme.colors.text
                    )
                }
            }


            AnimatedVisibility(
                visible = !viewModel.isRestoreClicked,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ){
                if (!viewModel.restoreStateResponse.success && viewModel.restoreStateResponse.error.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator(color = KeySafeTheme.colors.primary)
                    }
                }

                if (viewModel.restoreStateResponse.error.isNotEmpty()){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            text = viewModel.restoreStateResponse.error,
                            color = KeySafeTheme.colors.text,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                if (viewModel.restoreStateResponse.success){
                    LazyColumn(
                        modifier = Modifier
                            .padding(it)
                    ){

                        item {

                            LazyRow(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item {
                                    Box(modifier = Modifier.padding(KeySafeTheme.spaces.medium)){
                                        DrawerItem(
                                            icon = Icons.Default.Dashboard,
                                            text = stringResource(id = R.string.all_entries),
                                            isSelected = viewModel.selectedLabel == null,
                                            onClick = {
                                                viewModel.selectedLabel = null
                                                viewModel.getEntriesBy()
                                            }
                                        )
                                    }
                                }


                                items(viewModel.restoreStateResponse.backupData.labels){ label ->
                                    Box(modifier = Modifier.padding(KeySafeTheme.spaces.medium)){
                                        DrawerItem(
                                            icon = Icons.Default.Label,
                                            text = label.title,
                                            isSelected = viewModel.selectedLabel?.id == label.id,
                                            onClick = {
                                                viewModel.selectedLabel = label
                                                viewModel.getEntriesBy()
                                            }
                                        )
                                    }
                                }
                            }

                        }


                        item {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = KeySafeTheme.spaces.mediumLarge,
                                        horizontal = KeySafeTheme.spaces.large
                                    ),
                                text = stringResource(R.string.all_entries),
                                color = KeySafeTheme.colors.text
                            )

                        }

                        items(viewModel.entries){ entry ->

                            EntryItem(
                                entry = entry,
                                onEntryClick = { clickedEntry ->
                                    Toast.makeText(context, context.getString(R.string.you_can_t_view_entries_here), Toast.LENGTH_SHORT).show()
                                }
                            )
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Gray))

                        }

                        item {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = KeySafeTheme.spaces.mediumLarge,
                                        horizontal = KeySafeTheme.spaces.large
                                    ),
                                text = stringResource(R.string.archived_entries),
                                color = KeySafeTheme.colors.text
                            )

                        }

                        items(viewModel.archivedEntries){ entry ->

                            EntryItem(
                                entry = entry,
                                onEntryClick = { clickedEntry ->
                                    Toast.makeText(context, context.getString(R.string.you_can_t_view_entries_here), Toast.LENGTH_SHORT).show()
                                }
                            )
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Gray))

                        }

                        item {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = KeySafeTheme.spaces.mediumLarge,
                                        horizontal = KeySafeTheme.spaces.large
                                    ),
                                text = stringResource(R.string.entries_in_trash),
                                color = KeySafeTheme.colors.text
                            )

                        }

                        items(viewModel.trashedEntries){ entry ->

                            EntryItem(
                                entry = entry,
                                onEntryClick = { clickedEntry ->
                                    Toast.makeText(context, context.getString(R.string.you_can_t_view_entries_here), Toast.LENGTH_SHORT).show()
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









}