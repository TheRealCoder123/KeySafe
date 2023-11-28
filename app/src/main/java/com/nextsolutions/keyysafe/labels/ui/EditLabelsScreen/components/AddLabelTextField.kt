package com.nextsolutions.keyysafe.labels.ui.EditLabelsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import com.nextsolutions.keyysafe.ui.theme.White

@Composable
fun AddLabelTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDoneClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            modifier = Modifier.weight(1f),
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add),
            tint = KeySafeTheme.colors.iconTint
        )

        BasicTextField(
            modifier = Modifier.weight(5f),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TextStyle(
                color = KeySafeTheme.colors.text
            ),
            singleLine = true,
            decorationBox = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()){
                        Text(
                            text = stringResource(R.string.label_title),
                            color = KeySafeTheme.colors.text,
                        )
                    }
                }
                it()
            }
        )

        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { onDoneClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = stringResource(R.string.done),
                tint = KeySafeTheme.colors.iconTint
            )
        }


    }

    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(Gray))

}


@Preview
@Composable
fun AddLabelTextFieldPrev() {
    AddLabelTextField(
        value = "",
        onValueChange = {},
        onDoneClicked = {}
    )
}