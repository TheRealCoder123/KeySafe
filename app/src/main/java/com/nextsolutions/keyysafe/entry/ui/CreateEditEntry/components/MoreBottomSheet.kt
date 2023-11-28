package com.nextsolutions.keyysafe.entry.ui.CreateEditEntry.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.global_components.CustomSwitch
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun MoreBottomSheet(
    onAddLabel: () -> Unit,
    onAddNewField: () -> Unit,
    moveToTrashSwitchValue: Boolean,
    onMoveToTrashSwitchChecked: (Boolean) -> Unit,
    archiveSwitchValue: Boolean,
    onArchiveSwitchValue: (Boolean) -> Unit,
    isMoveToTrashSwitchVisible: Boolean,
    onAskForAuthentication: (Boolean) -> Unit,
    askForAuthenticationValue: Boolean,
    isTrashFull: Boolean
) {

    val spaceBetween = KeySafeTheme.spaces.mediumLarge


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spaceBetween),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Text(
                text = stringResource(id = R.string.more),
                color = KeySafeTheme.colors.text
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            GreenButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAddLabel()
                },
                label = stringResource(R.string.set_a_label)
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            GreenButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAddNewField()
                },
                label = stringResource(R.string.add_new_field)
            )

            if (isMoveToTrashSwitchVisible){
                Spacer(modifier = Modifier.height(spaceBetween))

                CustomSwitch(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.move_to_trash),
                    subtitle = stringResource(R.string.this_entry_is_moved_to_trash_where_it_can_be_deleted),
                    checked = moveToTrashSwitchValue,
                    onCheckedChange = { onMoveToTrashSwitchChecked(it) }
                )

                if (isTrashFull){
                    Text(
                        text = stringResource(R.string.trash_is_full_delete_some_entries),
                        color = Color.Red,
                    )
                }

            }

            Spacer(modifier = Modifier.height(spaceBetween))

            CustomSwitch(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.archive),
                subtitle = stringResource(R.string.this_entry_will_be_archived),
                checked = archiveSwitchValue,
                onCheckedChange = { onArchiveSwitchValue(it) }
            )

            Spacer(modifier = Modifier.height(spaceBetween))

//            CustomSwitch(
//                modifier = Modifier.fillMaxWidth(),
//                title = stringResource(R.string.ask_for_authentication),
//                subtitle = stringResource(R.string.with_this_feature_everyone_accessing_this_entry_would_need_to_authenticate),
//                checked = askForAuthenticationValue,
//                onCheckedChange = { onAskForAuthentication(it) }
//            )

        }

    }

}