package com.nextsolutions.keyysafe.FAQ.ui.FAQScreen.compopnents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.FAQ.domain.model.FAQuestion
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun QuestionView(
    faQuestion: FAQuestion,
    onClick: (FAQuestion) -> Unit
) {




    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(KeySafeTheme.spaces.mediumLarge))
            .background(KeySafeTheme.colors.dialogBgColor)
            .clickable {
                onClick(faQuestion)
            }
            .padding(KeySafeTheme.spaces.mediumLarge)
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = stringResource(id = R.string.question),
                tint = KeySafeTheme.colors.iconTint
            )

            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

            Text(
                text = faQuestion.question,
                color = KeySafeTheme.colors.text,
                fontSize = 18.sp
            )

        }

        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = faQuestion.answer,
            color = KeySafeTheme.colors.description
        )
//
//        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
//
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            Text(
//                text = "12/10/2004",
//                color = KeySafeTheme.colors.description,
//            )
//
//            Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))
//
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Text(
//                    text = "Read More",
//                    color = KeySafeTheme.colors.text,
//                )
//
//                Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))
//
//
//                Icon(
//                    imageVector = Icons.Default.ArrowCircleRight,
//                    contentDescription = stringResource(id = R.string.question),
//                    tint = KeySafeTheme.colors.iconTint
//                )
//
//            }
//
//        }
//



    }



}