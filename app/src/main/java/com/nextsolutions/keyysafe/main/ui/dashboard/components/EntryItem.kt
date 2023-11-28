package com.nextsolutions.keyysafe.main.ui.dashboard.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.common.util.Other
import com.nextsolutions.keyysafe.global_components.EntryImage
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EntryItem(
    modifier : Modifier = Modifier,
    entry: DashboardEntry,
    onEntryClick: (DashboardEntry) -> Unit,
    onLongClick: (DashboardEntry) -> Unit = {}
) {

    Row(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onEntryClick(entry)
                },
                onLongClick = {
                    onLongClick(entry)
                }
            )
            .fillMaxWidth()
            .padding(KeySafeTheme.spaces.mediumLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {

        EntryImage(
            entryTitle = entry.title,
            entryColor = entry.color,
            size = 50.dp
        )

        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = entry.title,
                    color = KeySafeTheme.colors.text,
                    fontSize = 20.sp
                )

                Text(
                    text = Other.formatTimestamp(entry.timeStamp),
                    color = Gray,
                    fontSize = 13.sp
                )
            }

            if (entry.subTitle.isNotEmpty()){
                Text(
                    text = entry.subTitle,
                    color = KeySafeTheme.colors.text,
                )
            }



        }



    }


}