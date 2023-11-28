package com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.global_components.BackTopAppBar
import com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components.AddLabelTextField
import com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components.EditLabelDialog
import com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components.LabelItem
import com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components.LabelOptionsDialog
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.White

@Composable
fun EditLabelsScreen(
    navController: NavHostController,
    viewModel: EditLabelsViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    LabelOptionsDialog(
        viewModel.isLabelOptionsDialogVisible,
        onDismiss = {
            viewModel.isLabelOptionsDialogVisible = false
            viewModel.selectedLabel = null
        },
        onDeleteLabel = {
            viewModel.deleteLabel()
        },
        onRenameLabel = {
            viewModel.isEditLabelDialogVisible = true
        }
    )

    EditLabelDialog(
        isVisible = viewModel.isEditLabelDialogVisible,
        onDismiss = { viewModel.isEditLabelDialogVisible = false },
        onEditDone = {
            viewModel.updateLabel(viewModel.editLabelTitleTextState, viewModel.selectedLabel?.id ?: 0)
        },
        titleTextValue = viewModel.editLabelTitleTextState,
        onTitleTextValueChange = {
            viewModel.editLabelTitleTextState = it
        }
    )


    Scaffold(
        backgroundColor = KeySafeTheme.colors.background,
        topBar = {
            BackTopAppBar(
                title = stringResource(id = R.string.edit_labels),
                onBackPress = { navController.navigateUp() }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ){

            item {
                AddLabelTextField(
                    value = viewModel.labelTitleTextState,
                    onValueChange = { viewModel.labelTitleTextState = it },
                    onDoneClicked = {
                        if (viewModel.labelTitleTextState.isEmpty()){
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_enter_the_label_title_first),
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            val label = Label(
                                0,
                                viewModel.labelTitleTextState,
                                false
                            )
                            viewModel.createLabel(label)
                        }
                    }
                )
            }

            items(viewModel.labels){ label ->
                LabelItem(
                    label = label,
                    onClick = { label1 ->
                        viewModel.isLabelOptionsDialogVisible = true
                        viewModel.selectedLabel = label1
                        viewModel.editLabelTitleTextState = label1.title
                    }
                )
                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))
            }

        }
    }

}