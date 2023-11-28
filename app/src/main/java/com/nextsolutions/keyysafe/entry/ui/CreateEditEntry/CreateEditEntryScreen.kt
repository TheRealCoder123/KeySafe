package com.nextsolutions.keyysafe.entry.ui.CreateEditEntry

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.GeneratingTokens
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker.PasswordStrength
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker.PasswordStrength.*
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components.AddNewFieldDialog
import com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components.ChooseColorDialog
import com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components.MoreBottomSheet
import com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components.SetALabelDialog
import com.nextsolutions.keyysafe.global_components.CustomTextField
import com.nextsolutions.keyysafe.global_components.EntryImage
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.Orange
import com.nextsolutions.keyysafe.ui.theme.White
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateEditEntryScreen(
    viewModel: CreateEditViewModel = hiltViewModel(),
    navController: NavController,
) {

    val marginBetweenFields = KeySafeTheme.spaces.mediumLarge


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val localUriHandler = LocalUriHandler.current

    var isSetALabelDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isAddNewFieldDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isColorsDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.get<String>(ArgumentKeys.PASS_GENERATED_PASSWORD_BACK)?.let {
                if (it.isNotEmpty()){
                    viewModel.updatePasswordField(it)
                }
            }
    }




    ChooseColorDialog(
        isVisible = isColorsDialogVisible,
        onColorSelected = {
            viewModel.entryColor = it.toArgb()
            isColorsDialogVisible = false
        },
        onDismiss = { isColorsDialogVisible = false },
        selectedColor = viewModel.entryColor
    )

    SetALabelDialog(
        isVisible = isSetALabelDialogVisible,
        onDismiss = { isSetALabelDialogVisible = false },
        labels = viewModel.labels,
        currentLabelId = viewModel.labelId,
        onLabelClicked =  {
            if (viewModel.labelId == it.id){
                viewModel.labelId = 0
            }else{
                viewModel.labelId = it.id
            }
        }
    )

    AddNewFieldDialog(
        fields = viewModel.fields.value,
        isVisible = isAddNewFieldDialogVisible,
        onDismiss = {
            isAddNewFieldDialogVisible = false
        },
        onDone = {
            viewModel.addNewField(it)
            isAddNewFieldDialogVisible = false
        }

    )


    BackHandler {
        if (sheetState.isVisible){
            scope.launch {
                sheetState.hide()
            }
        }else{
            navController.navigateUp()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            MoreBottomSheet(
                isTrashFull = viewModel.isTrashFull,
                onAddLabel = {
                    isSetALabelDialogVisible = true
                },
                onAddNewField = {
                    isAddNewFieldDialogVisible = true
                },
                isMoveToTrashSwitchVisible = viewModel.entryId != "null",
                moveToTrashSwitchValue = viewModel.moveToTrashSwitchValue,
                onMoveToTrashSwitchChecked = {
                    viewModel.moveToTrashSwitchValue = it
                    viewModel.archiveSwitchValue = false
                    viewModel.moveToTrash(viewModel.moveToTrashSwitchValue)
                    viewModel.archive(viewModel.archiveSwitchValue)
                },
                archiveSwitchValue = viewModel.archiveSwitchValue,
                onArchiveSwitchValue = {
                    viewModel.archiveSwitchValue = it
                    viewModel.moveToTrashSwitchValue = false
                    viewModel.moveToTrash(viewModel.moveToTrashSwitchValue)
                    viewModel.archive(viewModel.archiveSwitchValue)
                },
                onAskForAuthentication = {
                    viewModel.askForAuthenticationSwitchValue = it
                    viewModel.changeAskForAuth(viewModel.askForAuthenticationSwitchValue)
                },
                askForAuthenticationValue = viewModel.askForAuthenticationSwitchValue
            )
        },
        sheetBackgroundColor = KeySafeTheme.colors.dialogBgColor
    ) {


        Scaffold(
            backgroundColor = KeySafeTheme.colors.background,
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = {
                TopAppBar(
                    backgroundColor = KeySafeTheme.colors.primary,
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
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
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            EntryImage(
                                entryTitle = viewModel.entryTitleTextFieldState,
                                entryColor = viewModel.entryColor,
                                onClick = {
                                    isColorsDialogVisible = true
                                }
                            )

                            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                            Text(
                                text = viewModel.entryTitleTextFieldState,
                                color = White
                            )
                        }
                    },
                    actions = {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = KeySafeTheme.spaces.mediumLarge)
                                .clickable {
                                    viewModel.save()
                                    navController.navigateUp()
                                },
                            text = stringResource(R.string.save),
                            color = White
                        )
                    }
                )
            },
            floatingActionButton = {

                AnimatedVisibility(
                    visible = viewModel.showUndoButton,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally()
                ) {
                    GreenButton(
                        shape = RoundedCornerShape(100.dp),
                        onClick = {
                            viewModel.undo()
                        },
                        label = stringResource(R.string.undo_fields, viewModel.deletedFieldNumb.toString()),
                        icon = Icons.Filled.Undo
                    )
                }


            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(marginBetweenFields)
            ) {


                item {
                    CustomTextField(
                        value = viewModel.entryTitleTextFieldState,
                        onValueChange = { newValue ->
                            viewModel.entryTitleTextFieldState = newValue
                        },
                        label = stringResource(R.string.title),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Spacer(modifier = Modifier.height(marginBetweenFields))
                }

                items(viewModel.fields.value) { entryDataField ->


                    var passwordStrength by remember {
                        mutableStateOf<PasswordStrength?>(null)
                    }


                    LaunchedEffect(key1 = viewModel.fieldTextState) {
                        if (viewModel.fieldTextState != entryDataField.value) {
                            viewModel.updateFieldValue()
                        }

                        if (entryDataField.type == EntryDataFieldType.Password){
                            passwordStrength = if (viewModel.fieldTextState.isNotEmpty() && viewModel.currentlyFocusedField?.id == entryDataField.id){
                                PasswordChecker().checkPasswordStrength(viewModel.fieldTextState)
                            }else{
                                null
                            }
                        }

                    }

                    when (entryDataField.type) {
                        EntryDataFieldType.Account -> {


                            CustomTextField(
                                modifier = Modifier
                                    .onFocusChanged {
                                        if (it.isFocused){
                                            viewModel.currentlyFocusedField = entryDataField
                                            viewModel.fieldTextState = entryDataField.value
                                        }else{
                                            viewModel.currentlyFocusedField = null
                                            viewModel.fieldTextState = ""
                                        }
                                    },
                                value = if (viewModel.currentlyFocusedField?.id == entryDataField.id) viewModel.fieldTextState else entryDataField.value,
                                onValueChange = {
                                    viewModel.fieldTextState = it
                                },
                                label = stringResource(id = R.string.account),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.removeField(entryDataField)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.RemoveCircle,
                                            contentDescription = stringResource(id = R.string.remove),
                                            tint = KeySafeTheme.colors.iconTint
                                        )
                                    }
                                }
                            )
                        }
                        EntryDataFieldType.Username -> {
                            CustomTextField(
                                modifier = Modifier
                                    .onFocusChanged {
                                        if (it.isFocused){
                                            viewModel.currentlyFocusedField = entryDataField
                                            viewModel.fieldTextState = entryDataField.value
                                        }else{
                                            viewModel.currentlyFocusedField = null
                                            viewModel.fieldTextState = ""
                                        }
                                    },
                                value = if (viewModel.currentlyFocusedField?.id == entryDataField.id) viewModel.fieldTextState else entryDataField.value,
                                onValueChange = {
                                    viewModel.fieldTextState = it
                                },
                                label = stringResource(id = R.string.username),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.removeField(entryDataField)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.RemoveCircle,
                                            contentDescription = stringResource(id = R.string.remove),
                                            tint = KeySafeTheme.colors.iconTint
                                        )
                                    }
                                }
                            )
                        }


                        EntryDataFieldType.Password -> {

                            var showPassword by rememberSaveable {
                                mutableStateOf(false)
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .onFocusChanged {
                                            if (it.isFocused) {
                                                viewModel.currentlyFocusedField = entryDataField
                                                viewModel.fieldTextState = entryDataField.value
                                            } else {
                                                viewModel.currentlyFocusedField = null
                                                viewModel.fieldTextState = ""
                                            }
                                        },
                                    value = if (viewModel.currentlyFocusedField?.id == entryDataField.id) viewModel.fieldTextState else entryDataField.value,
                                    onValueChange = {
                                        viewModel.fieldTextState = it
                                    },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = KeySafeTheme.colors.text,
                                        cursorColor = if (viewModel.fieldTextState.isEmpty() && viewModel.currentlyFocusedField?.id == entryDataField.id) Orange else Green,
                                        focusedBorderColor = when(passwordStrength){
                                            STRONG -> Green
                                            MEDIUM -> Orange
                                            WEAK -> Color.Red
                                            null -> Orange
                                        },
                                        unfocusedBorderColor = Green
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    visualTransformation = if (showPassword) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(R.string.password),
                                            color = KeySafeTheme.colors.text
                                        )
                                    },
                                    trailingIcon = {
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (showPassword) {
                                                IconButton(onClick = { showPassword = false }) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Visibility,
                                                        contentDescription = stringResource(id = R.string.hide_show_password),
                                                        tint = KeySafeTheme.colors.iconTint
                                                    )
                                                }
                                            } else {
                                                IconButton(
                                                    onClick = { showPassword = true }) {
                                                    Icon(
                                                        imageVector = Icons.Filled.VisibilityOff,
                                                        contentDescription = stringResource(id = R.string.hide_show_password),
                                                        tint = KeySafeTheme.colors.iconTint
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.small))

                                            IconButton(onClick = {
                                                navController.navigate(MainNavigation.PasswordGeneratorScreen.passIfNeedsToSendBackResult(true))
                                                viewModel.hasNavigated = false
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Filled.GeneratingTokens,
                                                    contentDescription = stringResource(id = R.string.generate_password),
                                                    tint = KeySafeTheme.colors.iconTint
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.small))
                                            IconButton(
                                                onClick = {
                                                    viewModel.removeField(entryDataField)
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.RemoveCircle,
                                                    contentDescription = stringResource(id = R.string.remove),
                                                    tint = KeySafeTheme.colors.iconTint
                                                )
                                            }
                                        }

                                    },
                                    singleLine = true
                                )

                                AnimatedVisibility(
                                    visible = passwordStrength != null,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    if (passwordStrength != null){
                                        Text(
                                            text = when(passwordStrength){
                                                STRONG -> stringResource(id = R.string.password_is_strong)
                                                MEDIUM -> stringResource(id = R.string.password_is_medium)
                                                WEAK -> stringResource(id = R.string.password_is_weak)
                                                else -> "not detected"
                                            },
                                            color = when(passwordStrength){
                                                STRONG -> Green
                                                MEDIUM -> Orange
                                                WEAK -> Color.Red
                                                else -> Color.Red
                                            }
                                        )
                                    }
                                }


                            }
                        }
                        EntryDataFieldType.Note -> {
                            CustomTextField(
                                modifier = Modifier
                                    .onFocusChanged {
                                        if (it.isFocused){
                                            viewModel.currentlyFocusedField = entryDataField
                                            viewModel.fieldTextState = entryDataField.value
                                        }else{
                                            viewModel.currentlyFocusedField = null
                                            viewModel.fieldTextState = ""
                                        }
                                    },
                                value = if (viewModel.currentlyFocusedField?.id == entryDataField.id) viewModel.fieldTextState else entryDataField.value,
                                onValueChange = {
                                    viewModel.fieldTextState = it
                                },
                                label = stringResource(id = R.string.note),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                singleLine = false,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.removeField(entryDataField)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.RemoveCircle,
                                            contentDescription = stringResource(id = R.string.remove),
                                            tint = KeySafeTheme.colors.iconTint
                                        )
                                    }
                                }
                            )
                        }
                        EntryDataFieldType.Email -> {
                            CustomTextField(
                                modifier = Modifier
                                    .onFocusChanged {
                                        if (it.isFocused){
                                            viewModel.currentlyFocusedField = entryDataField
                                            viewModel.fieldTextState = entryDataField.value
                                        }else{
                                            viewModel.currentlyFocusedField = null
                                            viewModel.fieldTextState = ""
                                        }
                                    },
                                value = if (viewModel.currentlyFocusedField?.id == entryDataField.id) viewModel.fieldTextState else entryDataField.value,
                                onValueChange = {
                                    viewModel.fieldTextState = it
                                },
                                label = stringResource(id = R.string.email),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                singleLine = false,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.removeField(entryDataField)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.RemoveCircle,
                                            contentDescription = stringResource(id = R.string.remove),
                                            tint = KeySafeTheme.colors.iconTint
                                        )
                                    }
                                }
                            )
                        }
                        EntryDataFieldType.Custom -> {
                            CustomTextField(
                                modifier = Modifier
                                    .onFocusChanged {
                                        if (it.isFocused){
                                            viewModel.currentlyFocusedField = entryDataField
                                            viewModel.fieldTextState = entryDataField.value
                                        }else{
                                            viewModel.currentlyFocusedField = null
                                            viewModel.fieldTextState = ""
                                        }
                                    },
                                value = if (viewModel.currentlyFocusedField?.id == entryDataField.id) viewModel.fieldTextState else entryDataField.value,
                                onValueChange = {
                                    viewModel.fieldTextState = it
                                },
                                label = entryDataField.title,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                singleLine = false,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.removeField(entryDataField)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.RemoveCircle,
                                            contentDescription = stringResource(id = R.string.remove),
                                            tint = KeySafeTheme.colors.iconTint
                                        )
                                    }
                                }
                            )
                        }
                        EntryDataFieldType.Website -> {
                            CustomTextField(
                                modifier = Modifier
                                    .onFocusChanged {
                                        if (it.isFocused){
                                            viewModel.currentlyFocusedField = entryDataField
                                            viewModel.fieldTextState = entryDataField.value
                                        }else{
                                            viewModel.currentlyFocusedField = null
                                            viewModel.fieldTextState = ""
                                        }
                                    },
                                value = if (viewModel.currentlyFocusedField?.id == entryDataField.id) viewModel.fieldTextState else entryDataField.value,
                                onValueChange = {
                                    viewModel.fieldTextState = it
                                },
                                label = stringResource(id = R.string.website),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                singleLine = false,
                                trailingIcon = {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        IconButton(
                                            onClick = {
                                                val url = entryDataField.value
                                                val validUrl = if (url.startsWith("http://") || url.startsWith("https://")) {
                                                    url
                                                } else {
                                                    "https://$url"
                                                }
                                                val uri = Uri.parse(validUrl)
                                                if (uri.scheme != null && uri.host != null) {
                                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                                    context.startActivity(intent)
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        context.getString(R.string.invalid_url),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowOutward,
                                                contentDescription = stringResource(id = R.string.go_to_site),
                                                tint = KeySafeTheme.colors.iconTint
                                            )
                                        }

                                        IconButton(
                                            onClick = {
                                                viewModel.removeField(entryDataField)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.RemoveCircle,
                                                contentDescription = stringResource(id = R.string.remove),
                                                tint = KeySafeTheme.colors.iconTint
                                            )
                                        }

                                    }
                                }
                            )
                        }
                    }
                    if (viewModel.fields.value.last().id != entryDataField.id) {
                        Spacer(modifier = Modifier.height(marginBetweenFields))
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(marginBetweenFields))
                    GreenButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        label = stringResource(R.string.more)
                    )
                }

            }

        }

    }


}





