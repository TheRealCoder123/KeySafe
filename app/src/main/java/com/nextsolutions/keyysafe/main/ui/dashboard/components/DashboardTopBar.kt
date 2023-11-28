package com.nextsolutions.keyysafe.main.ui.dashboard.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.main.domain.enums.DashboardTopBarActionType
import com.nextsolutions.keyysafe.main.domain.model.TopBarActions
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun DashboardTopBar(
    modifier : Modifier = Modifier,
    actions: List<TopBarActions<DashboardTopBarActionType>>,
    onActionClick: (TopBarActions<DashboardTopBarActionType>) -> Unit,
    onNavigationClicked: () -> Unit,
    title: String = stringResource(id = R.string.app_name),
) {


    TopAppBar(
        modifier = modifier,
        backgroundColor = KeySafeTheme.colors.background,
        title = {
            Text(
                text = title,
                color = KeySafeTheme.colors.onBackgroundText
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationClicked() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.nav_icon),
                    tint = KeySafeTheme.colors.onBackgroundIconTint
                )
            }
        },
        actions = {
            actions.forEach {
                IconButton(onClick = { onActionClick(it) }) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription,
                        tint = KeySafeTheme.colors.onBackgroundIconTint
                    )
                }
            }
        }
    )





}