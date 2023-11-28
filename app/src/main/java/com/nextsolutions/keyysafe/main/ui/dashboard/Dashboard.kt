package com.nextsolutions.keyysafe.main.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.app.graphs.Navigation
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.util.Constants.NO_LABEL
import com.nextsolutions.keyysafe.common.util.rememberWindowInfo
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.global_components.PieChartComponent
import com.nextsolutions.keyysafe.main.domain.enums.DashboardTopBarActionType
import com.nextsolutions.keyysafe.main.domain.model.TopBarActions
import com.nextsolutions.keyysafe.main.ui.dashboard.components.DashboardTopBar
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerBody
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerHeader
import com.nextsolutions.keyysafe.main.ui.dashboard.drawer.DrawerItem
import com.nextsolutions.keyysafe.settings.ui.AutoFill.components.AnimatedBorderCard
import com.nextsolutions.keyysafe.trash.ui.TrashScreen.components.TrashEntryDialog
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import kotlinx.coroutines.launch

@Composable
fun Dashboard(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController
) {


    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val windowInfo = rememberWindowInfo()
    val scrollState = rememberScrollState()


    var isAutofillServiceEnabled by remember {
        mutableStateOf(false)
    }

//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        LaunchedEffect(key1 = Unit) {
//            isAutofillServiceEnabled = context.isAppSetAsAutofillService()
//        }
//    }


    val pieChartState = dashboardViewModel.pieChartData

    LaunchedEffect(key1 = dashboardViewModel.selectedLabel) {
        if (dashboardViewModel.selectedLabel != null) {
            dashboardViewModel.topBarTitleState =
                dashboardViewModel.selectedLabel?.title ?: context.getString(R.string.dashboard)
        } else {
            dashboardViewModel.topBarTitleState = context.getString(R.string.dashboard)
        }
    }

//    LaunchedEffect(key1 = dashboardViewModel.isAuthenticated) {
//        if (dashboardViewModel.isAuthenticated && !dashboardViewModel.hasNavigated) {
//            delay(200)
//            navController.navigate(MainNavigation.CreateEntryScreen.passEntryId(dashboardViewModel.clickedEntryId))
//            dashboardViewModel.isAuthenticated = false
//            dashboardViewModel.clickedEntryId = ""
//            dashboardViewModel.hasNavigated = true
//        }
//    }
//
//    LaunchedEffect(key1 = Unit) {
//        navController.currentBackStackEntry
//            ?.savedStateHandle
//            ?.get<Boolean>(ArgumentKeys.AUTH_SCREEN_CONFIRM_AUTH_RESULT_KEY)?.let {
//                dashboardViewModel.isAuthenticated = it
//            }
//    }

    LaunchedEffect(key1 = Unit) {
        dashboardViewModel.isPieChartAnimPlayed = true
    }

    TrashEntryDialog(
        isVisible = dashboardViewModel.isTrashEntryDialogVisible,
        onDismiss = { dashboardViewModel.isTrashEntryDialogVisible = false },
        onDelete = {
            dashboardViewModel.deleteEntry()
            dashboardViewModel.isTrashEntryDialogVisible = false
        },
        onRemoveFromTrash = {
            dashboardViewModel.removeFromTrash()
            dashboardViewModel.isTrashEntryDialogVisible = false
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DashboardTopBar(
                title = dashboardViewModel.topBarTitleState,
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
                onActionClick = {
                    when (it.type) {
                        DashboardTopBarActionType.Settings -> {
                            navController.navigate(Navigation.SettingsNavigation.route)
                        }

//                        DashboardTopBarActionType.Database -> {
//                            if (Other.hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                                //navController.navigate(SettingsNavigation.Database.route)
//                            }else{
//                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                val uri = Uri.fromParts("package", context.packageName, null)
//                                intent.data = uri
//                                context.startActivity(intent)
//                                Toast.makeText(
//                                    context,
//                                    context.getString(R.string.grant_the_allow_managemnt_of_all_files_permission_for_to_be_able_to_use_the_database),
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
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
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                GreenButton(
                    shape = RoundedCornerShape(100.dp),
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    label = stringResource(R.string.menu),
                    icon = Icons.Filled.MenuOpen,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = KeySafeTheme.colors.primary,
                        contentColor = KeySafeTheme.colors.text
                    )
                )

                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                FloatingActionButton(
                    onClick = {
                        navController.navigate(MainNavigation.CreateEntryScreen.passEntryId(null))
                    },
                    backgroundColor = Green
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.create_new_entry),
                        tint = KeySafeTheme.colors.iconTint
                    )
                }

            }
        },
        drawerContent = {
            LazyColumn(
                modifier = Modifier.padding(KeySafeTheme.spaces.mediumLarge)
            ) {
                item {
                    DrawerHeader()
                    DrawerBody(
                        labels = if (windowInfo.isTablet) emptyList() else dashboardViewModel.labels,
                        onLabelClick = {
                            dashboardViewModel.getEntriesBy(it)
                        },
                        onAllClick = {
                            dashboardViewModel.getEntriesBy(DashboardViewModel.NO_SELECTED_LABEL)
                        },
                        selectedLabel = dashboardViewModel.selectedLabel,
                        navController = navController
                    )
                }
            }
        },
        drawerBackgroundColor = KeySafeTheme.colors.drawerBgColor,
        drawerShape = RoundedCornerShape(
            topEnd = KeySafeTheme.spaces.mediumLarge,
            bottomEnd = KeySafeTheme.spaces.mediumLarge
        ),
        backgroundColor = KeySafeTheme.colors.background
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(bottom = KeySafeTheme.spaces.extraLarge)
        ) {

            if (windowInfo.isTablet) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(430.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {

                        PieChartComponent(
                            pieChartState = pieChartState,
                            isPieChartAnimPlayed = dashboardViewModel.isPieChartAnimPlayed,
                            title = stringResource(id = R.string.entry_password_analysis),
                            subTitle = stringResource(R.string.brief_analysis_of_how_many_of_your_passwords_are_weak_medium_and_strong),
                            onStrongClick = {
                                navController.navigate(
                                    MainNavigation
                                        .ViewAllEntriesByPassStrengthScreen
                                        .passArgs(
                                            PasswordChecker
                                                .PasswordStrength
                                                .STRONG,
                                            if (dashboardViewModel.selectedLabel != null) dashboardViewModel.selectedLabel?.id
                                                ?: NO_LABEL else NO_LABEL
                                        )
                                )
                            },
                            onMediumClick = {
                                navController.navigate(
                                    MainNavigation
                                        .ViewAllEntriesByPassStrengthScreen
                                        .passArgs(
                                            PasswordChecker
                                                .PasswordStrength
                                                .MEDIUM,
                                            if (dashboardViewModel.selectedLabel != null) dashboardViewModel.selectedLabel?.id ?: NO_LABEL else NO_LABEL
                                        )
                                )
                            },
                            onWeakClick = {
                                navController.navigate(
                                    MainNavigation
                                        .ViewAllEntriesByPassStrengthScreen
                                        .passArgs(
                                            PasswordChecker
                                                .PasswordStrength
                                                .WEAK,
                                            if (dashboardViewModel.selectedLabel != null) dashboardViewModel.selectedLabel?.id
                                                ?: NO_LABEL else NO_LABEL
                                        )
                                )
                            }
                        )


                        Column(
                            modifier = Modifier
                                .padding(KeySafeTheme.spaces.mediumLarge),
                            horizontalAlignment = Alignment.Start
                        ) {

                            Text(
                                text = stringResource(R.string.labels),
                                color = KeySafeTheme.colors.text,
                                textAlign = TextAlign.Start,
                                fontSize = 25.sp
                            )

                            Text(
                                text = stringResource(R.string.all_added_labels),
                                color = Gray,
                                textAlign = TextAlign.Start,
                            )

                            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                            Column(
                                modifier = Modifier
                                    .verticalScroll(scrollState),
                                horizontalAlignment = Alignment.Start
                            ) {
                                DrawerItem(
                                    icon = Icons.Default.Dashboard,
                                    text = stringResource(id = R.string.All),
                                    isSelected = dashboardViewModel.selectedLabel == null,
                                    onClick = {
                                        dashboardViewModel.getEntriesBy(DashboardViewModel.NO_SELECTED_LABEL)
                                    }
                                )

                                dashboardViewModel.labels.forEach { label ->
                                    DrawerItem(
                                        icon = Icons.Default.Label,
                                        text = label.title,
                                        isSelected = dashboardViewModel.selectedLabel?.id == label.id,
                                        onClick = {
                                            dashboardViewModel.getEntriesBy(label)
                                        }
                                    )
                                }
                            }


                        }


                    }
                }
            } else {
                item {
                    PieChartComponent(
                        pieChartState = pieChartState,
                        isPieChartAnimPlayed = dashboardViewModel.isPieChartAnimPlayed,
                        title = stringResource(id = R.string.entry_password_analysis),
                        subTitle = stringResource(R.string.brief_analysis_of_how_many_of_your_passwords_are_weak_medium_and_strong),
                        onStrongClick = {
                            navController.navigate(
                                MainNavigation
                                    .ViewAllEntriesByPassStrengthScreen
                                    .passArgs(
                                        PasswordChecker
                                            .PasswordStrength
                                            .STRONG,
                                        if (dashboardViewModel.selectedLabel != null) dashboardViewModel.selectedLabel?.id
                                            ?: NO_LABEL else NO_LABEL
                                    )
                            )
                        },
                        onMediumClick = {
                            navController.navigate(
                                MainNavigation
                                    .ViewAllEntriesByPassStrengthScreen
                                    .passArgs(
                                        PasswordChecker
                                            .PasswordStrength
                                            .MEDIUM,
                                        if (dashboardViewModel.selectedLabel != null) dashboardViewModel.selectedLabel?.id
                                            ?: NO_LABEL else NO_LABEL
                                    )
                            )
                        },
                        onWeakClick = {
                            navController.navigate(
                                MainNavigation
                                    .ViewAllEntriesByPassStrengthScreen
                                    .passArgs(
                                        PasswordChecker
                                            .PasswordStrength
                                            .WEAK,
                                        if (dashboardViewModel.selectedLabel != null) dashboardViewModel.selectedLabel?.id
                                            ?: NO_LABEL else NO_LABEL
                                    )
                            )
                        }
                    )
                }
            }



            item {

//                AnimatedVisibility(
//                    visible = !isAutofillServiceEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && dashboardViewModel.isAutofillServiceDialogVisible,
//                    exit = fadeOut() + shrinkHorizontally(),
//                    enter = fadeIn() + expandHorizontally()
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .padding(KeySafeTheme.spaces.mediumLarge)
//                            .fillMaxWidth()
//                    ) {
//                        AnimatedBorderCard(
//                            shape = RoundedCornerShape(KeySafeTheme.spaces.mediumLarge),
//                            onCardClick = {
//                                navController.navigate(MainNavigation.AutoFill.route)
//                            }
//                        ) {
//
//
//                            Column(
//                                modifier = Modifier
//                                    .padding(KeySafeTheme.spaces.mediumLarge)
//                                    .fillMaxWidth(),
//                                horizontalAlignment = Alignment.Start,
//                            ) {
//
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    horizontalArrangement = Arrangement.SpaceBetween
//                                ) {
//                                    Text(
//                                        modifier = Modifier
//                                            .weight(1f),
//                                        text = stringResource(R.string.autofill_service),
//                                        color = KeySafeTheme.colors.text,
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Bold,
//                                    )
//
//                                    Icon(
//                                        modifier = Modifier
//                                            .clickable {
//                                                dashboardViewModel.isAutofillServiceDialogVisible =
//                                                    false
//                                            },
//                                        imageVector = Icons.Default.Close,
//                                        contentDescription = stringResource(id = R.string.close),
//                                        tint = KeySafeTheme.colors.iconTint
//                                    )
//
//                                }
//
//
//                                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))
//
//                                Text(
//                                    text = stringResource(R.string.seems_like_you_do_not_have_autofill_for_keysafe_click_and_turn_it_on),
//                                    color = KeySafeTheme.colors.text,
//                                    fontWeight = FontWeight.Medium
//                                )
//
//                            }
//
//
//                        }
//                    }
//                }


                AnimatedVisibility(
                    visible = dashboardViewModel.isTrashFull && dashboardViewModel.isTrashDialogVisible,
                    exit = fadeOut() + shrinkHorizontally(),
                    enter = fadeIn() + expandHorizontally()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(KeySafeTheme.spaces.mediumLarge)
                            .fillMaxWidth()
                    ) {
                        AnimatedBorderCard(
                            shape = RoundedCornerShape(KeySafeTheme.spaces.mediumLarge),
                            onCardClick = {
                                navController.navigate(MainNavigation.TrashScreen.route)
                            }
                        ) {


                            Column(
                                modifier = Modifier
                                    .padding(KeySafeTheme.spaces.mediumLarge)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                            ) {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.trash_is_full),
                                        color = KeySafeTheme.colors.text,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .clickable {
                                                dashboardViewModel.isTrashDialogVisible = false
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(id = R.string.close),
                                        tint = KeySafeTheme.colors.iconTint
                                    )

                                }


                                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

                                Text(
                                    text = stringResource(R.string.please_delete_some_entries_you_have_in_trash_to_be_able_to_store_in_it_again),
                                    color = KeySafeTheme.colors.text,
                                    fontWeight = FontWeight.Medium
                                )

                            }


                        }
                    }
                }

            }


            item {
                Row(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                MainNavigation.ViewAllEntriesScreen.passLabelId(
                                    if (dashboardViewModel.selectedLabel != null)
                                        dashboardViewModel.selectedLabel?.id
                                            ?: MainNavigation.ViewAllEntriesScreen.VIEW_ALL_ENTRIES
                                    else
                                        MainNavigation.ViewAllEntriesScreen.VIEW_ALL_ENTRIES
                                )
                            )
                        }
                        .fillMaxWidth()
                        .padding(KeySafeTheme.spaces.mediumLarge),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(R.string.recent_entries),
                        color = KeySafeTheme.colors.text,
                        fontSize = 20.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.view_all),
                            color = KeySafeTheme.colors.text
                        )
                        Icon(
                            modifier = Modifier.size(15.dp),
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = stringResource(R.string.view_all),
                            tint = KeySafeTheme.colors.iconTint
                        )
                    }

                }
            }




            if (dashboardViewModel.entries.isNotEmpty()) {

                items(dashboardViewModel.entries) { entry ->

                    EntryItem(
                        entry = entry,
                        onEntryClick = { clickedEntry ->
                            navController.navigate(
                                MainNavigation.CreateEntryScreen.passEntryId(
                                    clickedEntry.entryId
                                )
                            )
//                                if (clickedEntry.askForAuth){
//                                    dashboardViewModel.hasNavigated = false
//                                    dashboardViewModel.isAuthenticated = false
//                                    dashboardViewModel.clickedEntryId = clickedEntry.entryId
//                                    navController.navigate(MainNavigation.AuthScreen.passScreenUsability(AuthScreenUsability.CONFIRM_AUTHENTICATION))
//                                }else{
//                                    navController.navigate(MainNavigation.CreateEntryScreen.passEntryId(clickedEntry.entryId))
//                                }
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray)
                    )

                }
            } else {

                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = KeySafeTheme.spaces.mediumLarge),
                        text = stringResource(R.string.you_have_no_recent_entries),
                        color = Orange,
                        textAlign = TextAlign.Center
                    )
                }

            }


        }


    }
}