package com.nextsolutions.keyysafe.analytics.ui.analytics_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.analytics.domain.models.SelectEntryModel
import com.nextsolutions.keyysafe.global_components.EntryImage
import com.nextsolutions.keyysafe.main.domain.model.DashboardEntry
import com.nextsolutions.keyysafe.main.ui.dashboard.components.EntryItem
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun AllEntriesBottomSheet(
    entries: List<DashboardEntry> = emptyList(),
    onClick: (DashboardEntry) -> Unit,
    onLongClick: (DashboardEntry) -> Unit
) {


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(KeySafeTheme.spaces.mediumLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
                Text(
                    text = stringResource(id = R.string.all_entries),
                    color = KeySafeTheme.colors.text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(R.string.select_an_entry_you_want_to_check_by),
                    color = Gray,
                )
                Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
            }

        }

        items(entries){ dashboardEntry ->
            EntryItem(
                entry = dashboardEntry,
                onEntryClick = {
                    onClick(it)
                },
                onLongClick = {
                    onLongClick(it)
                }
            )
        }



    }

}


@Composable
fun EntryView(
    entryModel: SelectEntryModel,
    onClick: (SelectEntryModel) -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
            .clickable {
                onClick(entryModel)
            }
            .padding(KeySafeTheme.spaces.mediumLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EntryImage(
            entryTitle = entryModel.title,
            entryColor = entryModel.color,
            size = 80.dp
        )

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

        Text(
            text = entryModel.title,
            color = KeySafeTheme.colors.text
        )


    }

}