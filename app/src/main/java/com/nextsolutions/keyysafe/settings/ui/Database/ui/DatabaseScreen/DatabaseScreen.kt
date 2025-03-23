package com.nextsolutions.keyysafe.settings.ui.Database.ui.DatabaseScreen

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.MainViewModel
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.app.graphs.SettingsNavigation
import com.nextsolutions.keyysafe.auth.domain.enums.AuthScreenUsability
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.global_components.CustomSwitch
import com.nextsolutions.keyysafe.settings.settings_components.TextComponent
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BACKUP_DATA_PASSWORD
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackUpResponse
import com.nextsolutions.keyysafe.settings.ui.Database.domain.enums.AuthenticateFor
import com.nextsolutions.keyysafe.settings.ui.Database.ui.DatabaseScreen.components.BackupFileNameDialog
import com.nextsolutions.keyysafe.settings.ui.Database.ui.DatabaseScreen.components.BackupFilesBottomSheet
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DatabaseScreen(
    navHostController: NavHostController,
    viewModel: DatabaseViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit){
        navHostController.currentBackStackEntry
            ?.savedStateHandle
            ?.get<Boolean>(ArgumentKeys.AUTH_SCREEN_CONFIRM_AUTH_RESULT_KEY)?.let {
                viewModel.isAuthenticated = it
            }
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.authenticateFor = AuthenticateFor.BACK_UP_DATABASE
                viewModel.hasNavigated = false
                navHostController.navigate(MainNavigation.AuthScreen.passScreenUsability(AuthScreenUsability.CONFIRM_AUTHENTICATION))
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.permission_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )



    LaunchedEffect(key1 = viewModel.backupStateResponse){
        if (viewModel.backupStateResponse.success){
            Toast.makeText(
                context,
                context.getString(R.string.successfully_backed_up_database),
                Toast.LENGTH_SHORT
            ).show()
        }else{
            if (viewModel.backupStateResponse.error.isNotEmpty()){
                Toast.makeText(
                    context,
                    viewModel.backupStateResponse.error,
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
                Toast.makeText(
                    context,
                    context.getString(R.string.grant_the_allow_managemnt_of_all_files_permission_for_to_be_able_to_use_the_database),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        viewModel.backupStateResponse = BackUpResponse()
    }

    LaunchedEffect(key1 = viewModel.restoreStateResponse){
        if (viewModel.restoreStateResponse.success){
            Toast.makeText(
                context,
                context.getString(R.string.successful_restored_database_from_file),
                Toast.LENGTH_SHORT
            ).show()
        }else{
            if (viewModel.restoreStateResponse.error.isNotEmpty()){
                Toast.makeText(
                    context,
                    viewModel.restoreStateResponse.error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )



    LaunchedEffect(key1 = viewModel.isAuthenticated){
        if (viewModel.isAuthenticated && !viewModel.hasNavigated){
            when(viewModel.authenticateFor){
                AuthenticateFor.BACK_UP_DATABASE -> {
                    viewModel.isBackupNameDialogVisible = true
                    viewModel.hasNavigated = true
                }
                AuthenticateFor.SHARE_DATABASE -> {
                    scope.launch {
                        sheetState.show()
                        viewModel.hasNavigated = true
                    }
                }
            }
        }
    }

    BackupFileNameDialog(
        isVisible = viewModel.isBackupNameDialogVisible,
        onDone = { fileName ->
            if (fileName.isNotEmpty()){
                viewModel.backup(fileName.trim(), BACKUP_DATA_PASSWORD)
                viewModel.isBackupNameDialogVisible = false
            }else{
                Toast.makeText(context, "Please enter a file name", Toast.LENGTH_SHORT).show()
            }
        },
        onDismiss = {
            viewModel.isBackupNameDialogVisible = false
        }
    )




    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            BackupFilesBottomSheet(
                onClick = { file ->
                    val encoded = Uri.encode(file.toUri().toString().replace('%','|'))
                    navHostController.navigate(SettingsNavigation.ViewFileDataScreen.passData(encoded))
                },
                mainViewModel
            )
        },
        sheetBackgroundColor = KeySafeTheme.colors.dialogBgColor,
    ){
        Scaffold(
            backgroundColor = KeySafeTheme.colors.background,
            topBar = {
                BackTopAppBar(
                    title = stringResource(id = R.string.database),
                    onBackPress = {
                        navHostController.navigateUp()
                    }
                )
            }
        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(KeySafeTheme.spaces.mediumLarge)
            ){

                item {

                    TextComponent(
                        title = stringResource(R.string.backup_database),
                        subTitle = stringResource(R.string.create_a_backup_of_the_database),
                        onClick = {
                            viewModel.authenticateFor = AuthenticateFor.BACK_UP_DATABASE
                            viewModel.hasNavigated = false
                            navHostController.navigate(MainNavigation.AuthScreen.passScreenUsability(AuthScreenUsability.CONFIRM_AUTHENTICATION))
                        }
                    )

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                    TextComponent(
                        title = stringResource(R.string.restore_database),
                        subTitle = stringResource(R.string.restore_a_backup_of_the_database),
                        onClick = {
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                    CustomSwitch(
                        title = stringResource(R.string.auto_backup),
                        subtitle = stringResource(R.string.create_auto_backups_of_an_encrypted_database_the_app_automatically_saves_and_updates_the_backup_file),
                        checked = viewModel.autoBackUp,
                        onCheckedChange = {
                            viewModel.autoBackUp = it
                            viewModel.setAutoBack()
                        }
                    )

                }

            }
        }
    }


}