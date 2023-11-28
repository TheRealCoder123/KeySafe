package com.nextsolutions.keyysafe.global_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClearTextField: () -> Unit,
    textColor: Color = KeySafeTheme.colors.text,
    background: Color = KeySafeTheme.colors.searchTFBackground,
    verticalPadding: Dp = 20.dp,
    onSearchTFBG: Color = KeySafeTheme.colors.onSearchTFBackground,
    idleText: String = "",
) {

    
    var isAnimationPlayed by rememberSaveable {
        mutableStateOf(false)
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .padding(
                vertical = verticalPadding,
                horizontal = KeySafeTheme.spaces.mediumLarge
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search),
                tint = onSearchTFBG
            )
            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.medium))
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                singleLine = true,
                textStyle = TextStyle(
                    color = textColor
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = idleText.ifEmpty { stringResource(R.string.search_dots) },
                                color = onSearchTFBG,
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = onSearchTFBG,
                    modifier = Modifier.clickable {
                        onClearTextField()
                    }
                )
                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.medium))
            }
        }
    }



}

