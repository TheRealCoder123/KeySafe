package com.nextsolutions.keyysafe.main.ui.dashboard_2_0

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.app.graphs.Navigation
import com.nextsolutions.keyysafe.common.util.rememberWindowInfo
import com.nextsolutions.keyysafe.main.domain.enums.DashboardTopBarActionType
import com.nextsolutions.keyysafe.main.domain.model.TopBarActions
import com.nextsolutions.keyysafe.main.ui.dashboard.components.DashboardTopBar
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerBody
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerHeader
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerItem
import com.nextsolutions.keyysafe.main.ui.dashboard_2_0.components.BoxComponent
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import kotlinx.coroutines.launch

@Composable
fun Dashboard(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController
) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val windowInfo = rememberWindowInfo()

    val viewState = dashboardViewModel.state.value


    Scaffold(
        scaffoldState = scaffoldState,
        drawerBackgroundColor = KeySafeTheme.colors.drawerBgColor,
        drawerShape = RoundedCornerShape(topEnd = KeySafeTheme.spaces.mediumLarge, bottomEnd = KeySafeTheme.spaces.mediumLarge),
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            DashboardTopBar(
                title = stringResource(id = R.string.dashboard),
                actions = listOf(
//                    TopBarActions(
//                        Icons.Default.Backup,
//                        stringResource(id = R.string.database),
//                        DashboardTopBarActionType.Database
//                    ),
                    TopBarActions(
                        Icons.Default.Settings,
                        stringResource(id = R.string.settings),
                        DashboardTopBarActionType.Settings
                    )
                ),
                onActionClick ={
                    when(it.type){
                        DashboardTopBarActionType.Settings -> {
                            navController.navigate(Navigation.SettingsNavigation.route)
                        }

//                        DashboardTopBarActionType.Database -> {
//                            navController.navigate(SettingsNavigation.Database.route)
//                        }
                    }
                },
                onNavigationClicked = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            LazyColumn(
                modifier = Modifier.padding(KeySafeTheme.spaces.mediumLarge)
            ) {
                item{
                    DrawerHeader()
                    DrawerBody(
                        labels = emptyList(),
                        onLabelClick = {},
                        onAllClick = {},
                        selectedLabel = null,
                        navController = navController
                    )
                }
            }
        },
        floatingActionButton = {

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                    .background(KeySafeTheme.colors.primary)
                    .clickable {
                        navController.navigate(MainNavigation.CreateEntryScreen.passEntryId(null))
                    }
                    .padding(KeySafeTheme.spaces.mediumLarge),
                contentAlignment = Alignment.Center
            ){

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add),
                    tint = KeySafeTheme.colors.iconTint
                )

            }

        }
    ) {

        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(bottom = KeySafeTheme.spaces.extraLarge)
        ) {


            item {

                if (windowInfo.isTablet || windowInfo.screenHeight < windowInfo.screenWidth){

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Box(modifier = Modifier
                            .padding(horizontal = KeySafeTheme.spaces.medium)
                            .weight(1f)){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                                    .background(KeySafeTheme.colors.drawerBgColor)
                                    .clickable {
                                        navController.navigate(
                                            MainNavigation.ViewAllEntriesScreen.passLabelId(
                                                MainNavigation.ViewAllEntriesScreen.VIEW_ALL_ENTRIES
                                            )
                                        )
                                    }
                                    .padding(KeySafeTheme.spaces.mediumLarge),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(id = R.string.search),
                                    tint = KeySafeTheme.colors.iconTint
                                )

                                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                                Text(
                                    text = stringResource(R.string.search_entries),
                                    color = KeySafeTheme.colors.description,
                                    fontSize = 18.sp
                                )

                            }
                        }


                        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                        Box(modifier = Modifier
                            .padding(horizontal = KeySafeTheme.spaces.medium)
                            .weight(1f)){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                                    .background(KeySafeTheme.colors.drawerBgColor)
                                    .clickable {}
                                    .padding(KeySafeTheme.spaces.mediumLarge),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.SettingsSuggest,
                                    contentDescription = stringResource(id = R.string.suggestions),
                                    tint = KeySafeTheme.colors.iconTint
                                )

                                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                                Text(
                                    text = stringResource(R.string.suggestions),
                                    color = KeySafeTheme.colors.description,
                                    fontSize = 18.sp
                                )

                            }
                        }

                    }

                }else{

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                    Box(modifier = Modifier.padding(horizontal = KeySafeTheme.spaces.medium)){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                                .background(KeySafeTheme.colors.drawerBgColor)
                                .clickable {
                                    navController.navigate(
                                        MainNavigation.ViewAllEntriesScreen.passLabelId(
                                            MainNavigation.ViewAllEntriesScreen.VIEW_ALL_ENTRIES
                                        )
                                    )
                                }
                                .padding(KeySafeTheme.spaces.mediumLarge),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.search),
                                tint = KeySafeTheme.colors.iconTint
                            )

                            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                            Text(
                                text = stringResource(R.string.search_entries),
                                color = KeySafeTheme.colors.description,
                                fontSize = 18.sp
                            )

                        }
                    }



                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                    Box(modifier = Modifier.padding(horizontal = KeySafeTheme.spaces.medium)){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
                                .background(KeySafeTheme.colors.drawerBgColor)
                                .clickable {}
                                .padding(KeySafeTheme.spaces.mediumLarge),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.SettingsSuggest,
                                contentDescription = stringResource(id = R.string.suggestions),
                                tint = KeySafeTheme.colors.iconTint
                            )

                            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                            Text(
                                text = stringResource(R.string.suggestions),
                                color = KeySafeTheme.colors.description,
                                fontSize = 18.sp
                            )

                        }
                    }

                }



            }


            item {

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    item {
                         Spacer(modifier = Modifier.width(KeySafeTheme.spaces.medium))


                        BoxComponent(
                            onClick = {
                                navController.navigate(MainNavigation.ArchiveScreen.route)
                            },
                            label = stringResource(id = R.string.archive),
                            icon = Icons.Default.Archive
                        )

                        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))


                        BoxComponent(
                            onClick = {
                                navController.navigate(MainNavigation.TrashScreen.route)
                            },
                            label = stringResource(id = R.string.trash),
                            icon = Icons.Default.RestoreFromTrash
                        )

                        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                        BoxComponent(
                            onClick = {
                                navController.navigate(MainNavigation.AnalyticsScreen.route)
                            },
                            label = stringResource(R.string.analytics),
                            icon = Icons.Default.Analytics
                        )


                    }

                }

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))


            }


            item {

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    item {
                        Box(modifier = Modifier.padding(KeySafeTheme.spaces.medium)){
                            DrawerItem(
                                icon = Icons.Default.Dashboard,
                                text = stringResource(id = R.string.all_entries),
                                isSelected = viewState.selectedLabelId == DashboardViewModel.NO_SELECTED_LABEL,
                                onClick = {
                                    dashboardViewModel.getEntries(DashboardViewModel.NO_SELECTED_LABEL)
                                }
                            )
                        }
                    }


                    items(viewState.labels){ label ->
                        Box(modifier = Modifier.padding(KeySafeTheme.spaces.medium)){
                            DrawerItem(
                                icon = Icons.Default.Label,
                                text = label.title,
                                isSelected = viewState.selectedLabelId == label.id,
                                onClick = {
                                    dashboardViewModel.getEntries(label.id)
                                }
                            )
                        }
                    }

                }


            }

            item {

                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                Row(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                MainNavigation.ViewAllEntriesScreen.passLabelId(
                                    if (viewState.selectedLabelId != DashboardViewModel.NO_SELECTED_LABEL)
                                        viewState.selectedLabelId
                                    else
                                        MainNavigation.ViewAllEntriesScreen.VIEW_ALL_ENTRIES
                                )
                            )
                        }
                        .fillMaxWidth()
                        .padding(KeySafeTheme.spaces.mediumLarge),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = stringResource(id = R.string.recent_entries),
                        color = KeySafeTheme.colors.text,
                        fontSize = 18.sp
                    )

                    Row(
                        modifier = Modifier
                            .padding(KeySafeTheme.spaces.small),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.view_all),
                            color = KeySafeTheme.colors.text
                        )
                        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = stringResource(R.string.view_all),
                            tint = KeySafeTheme.colors.text
                        )
                    }

                }

            }

            items(viewState.entries){ entry ->
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