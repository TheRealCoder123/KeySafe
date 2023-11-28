package com.nextsolutions.keyysafe.FAQ.ui.FAQScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nextsolutions.keyysafe.FAQ.ui.FAQScreen.compopnents.QuestionView
import com.nextsolutions.keyysafe.R
import com.nextsolutions.keyysafe.global_components.SearchTextField
import com.nextsolutions.keyysafe.ui.theme.Black
import com.nextsolutions.keyysafe.ui.theme.KeySafeTheme

@Composable
fun FAQScreen(
    navHostController: NavHostController,
    faqViewModel: FAQViewModel = hiltViewModel(),
) {

    val screenState = faqViewModel.state.value

    LaunchedEffect(key1 = screenState.searchTextState){
        faqViewModel.searchFaqs(screenState.searchTextState)
     }

    LazyColumn {

        item {

             Column(
                 modifier = Modifier
                     .fillMaxWidth()
                     .background(KeySafeTheme.colors.primary)
                     .padding(25.dp),
                 horizontalAlignment = Alignment.Start
             ) {

                 Row(
                     horizontalArrangement = Arrangement.Center,
                     verticalAlignment = Alignment.CenterVertically
                 ) {

                     IconButton(onClick = { navHostController.navigateUp() }) {
                         Icon(
                             imageVector = Icons.Default.ArrowBackIosNew,
                             contentDescription = stringResource(id = R.string.back),
                             tint = Color.White
                         )
                     }

                     Text(
                         text = stringResource(R.string.frequently_asked_questions),
                         color = Color.White
                     )

                 }

                 Spacer(modifier = Modifier.height(KeySafeTheme.spaces.mediumLarge))

                 SearchTextField(
                     value = screenState.searchTextState,
                     onValueChange = {
                         faqViewModel.updateSearchTextState(it)
                     },
                     onClearTextField = {
                         faqViewModel.clearSearch()
                     },
                     textColor = Color.White,
                     background = Black,
                     verticalPadding = 17.dp,
                     onSearchTFBG = Color.White,
                     idleText = "Search..."
                 )

             }

        }

        items(screenState.faqs){
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(KeySafeTheme.spaces.mediumLarge)
            ){
                QuestionView(
                    faQuestion = it,
                    onClick = { faQuestion ->

                    }
                )
            }
        }


    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FAQScreenPrev() {
    FAQScreen(navHostController = rememberNavController())
}