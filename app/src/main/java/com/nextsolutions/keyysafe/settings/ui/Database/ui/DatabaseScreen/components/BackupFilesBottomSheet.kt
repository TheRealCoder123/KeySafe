package com.nextsolutions.keyysafe.settings.ui.Database.ui.DatabaseScreen.components

import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.app.MainViewModel
import com.nextsolutions.keyysafe.common.util.Other.getFileFromContentUri
import com.nextsolutions.keyysafe.global_components.GreenButton
import com.nextsolutions.keyysafe.ui.theme.Gray
import com.nextsolutions.keyysafe.ui.theme.Green
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BackupFilesBottomSheet(
    onClick: (File) -> Unit,
    mainViewModel: MainViewModel
) {



    val context = LocalContext.current

    val files = remember {
        val directory = File(Environment.getExternalStorageDirectory(), "KeySafeBackup")
        directory.listFiles()?.toList()?.sortedByDescending { it?.lastModified() } ?: emptyList<File?>()
    }

    val chooseOtherLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = {
            mainViewModel.shouldOpenAuthScreen = false
            if (it != null){
                val file = context.getFileFromContentUri(it)
                if (file != null){
                    onClick(file)
                }
            }
        }
    )

    LazyColumn(
        modifier = Modifier.padding(KeySafeTheme.spaces.mediumLarge)
    ) {


        item {

            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
            Text(
                text = stringResource(R.string.select_a_file),
                color = KeySafeTheme.colors.text,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = stringResource(R.string.select_a_file_you_want_to_restore),
                color = Gray,
            )
            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
        }

        item {
            Text(
                text = stringResource(R.string.your_backups),
                color = KeySafeTheme.colors.text,
            )
            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
        }

        if (files.isEmpty()){
           item {
               Text(
                   modifier = Modifier.fillMaxWidth(),
                   text = stringResource(R.string.seems_like_you_dont_have_any_backups),
                   color = KeySafeTheme.colors.text,
                   textAlign = TextAlign.Center
               )
               Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
           }
        }

        items(files){ file ->
            val latestBackup = files.sortedByDescending { it.lastModified() }[0] == file
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(file)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.FileOpen,
                        contentDescription = stringResource(R.string.file),
                        tint = KeySafeTheme.colors.iconTint
                    )

                    Spacer(modifier = Modifier.width(KeySafeTheme.spaces.mediumLarge))

                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        val lastModified = file.lastModified()
                        val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(lastModified)

                        Text(
                            text = file.name,
                            color = KeySafeTheme.colors.text,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(KeySafeTheme.spaces.small))

                        Text(
                            text = formattedDate,
                            color = Gray,
                        )
                    }
                }



                if (latestBackup){
                    Box(modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Green)
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
            GreenButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    chooseOtherLauncher.launch(arrayOf("*/*"))
                },
                label = "Choose Other"
            )
            Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))
        }

    }



}