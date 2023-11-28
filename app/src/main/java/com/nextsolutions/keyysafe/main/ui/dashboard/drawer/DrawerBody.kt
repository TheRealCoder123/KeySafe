package com.nextsolutions.keyysafe.main.ui.dashboard.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.graphs.MainNavigation
import com.nextsolutions.keyysafe.app.graphs.Navigation
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun DrawerBody(
    labels: List<Label>,
    onLabelClick: (Label) -> Unit,
    onAllClick: () -> Unit,
    selectedLabel: Label?,
    navController: NavController
) {

    Column {

       DrawerSection {
           DrawerItem(
               icon = Icons.Default.Dashboard,
               text = stringResource(id = R.string.dashboard),
               isSelected = selectedLabel == null,
               onClick = {
                   onAllClick()
               }
           )

           labels.forEach {
               DrawerItem(
                   icon = Icons.Default.Label,
                   text = it.title,
                   isSelected = selectedLabel?.id == it.id,
                   onClick = {
                       onLabelClick(it)
                   }
               )
           }
       }

        DrawerSection {
            DrawerItem(
                icon = Icons.Default.Archive,
                text = stringResource(R.string.archive),
                onClick = {
                    navController.navigate(MainNavigation.ArchiveScreen.route)
                }
            )
            DrawerItem(
                icon = Icons.Default.DeleteForever,
                text = stringResource(R.string.trash),
                onClick = {
                    navController.navigate(MainNavigation.TrashScreen.route)
                }
            )
        }

        DrawerSection {
            DrawerItem(
                icon = Icons.Default.Edit,
                text = stringResource(R.string.edit_labels),
                onClick = {
                    navController.navigate(MainNavigation.EditLabelsScreen.route)
                }
            )
            DrawerItem(
                icon = Icons.Default.Password,
                text = stringResource(R.string.password_generator),
                onClick = {
                    navController.navigate(MainNavigation.PasswordGeneratorScreen.passIfNeedsToSendBackResult(false))
                }
            )
            DrawerItem(
                icon = Icons.Default.Settings,
                text = stringResource(R.string.settings),
                onClick = {
                    navController.navigate(Navigation.SettingsNavigation.route)
                }
            )
        }

    }


}


@Composable
fun DrawerSection(
    content: @Composable () -> Unit
) {

    Column {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Gray))
        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))
        content()
        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Gray))

    }

}


@Composable
fun DrawerItem(
    icon: ImageVector,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {

    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(KeySafeTheme.spaces.medium))
            .background(if (isSelected) KeySafeTheme.colors.selected else KeySafeTheme.colors.drawerBgColor)
            .clickable {
                onClick()
            }
            .padding(horizontal = KeySafeTheme.spaces.medium, vertical = KeySafeTheme.spaces.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = KeySafeTheme.colors.iconTint
        )
        Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))
        Text(
            text = text,
            color = KeySafeTheme.colors.text
        )
    }
    Spacer(modifier = Modifier.height(KeySafeTheme.spaces.medium))
}