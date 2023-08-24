package com.nextsolutions.keyysafe.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import com.upnext.notabox.presentation.ui.theme.Spaces
import com.upnext.notabox.presentation.ui.theme.Typography


private val DarkColors = darkColors(
    primary = Green,
    iconTint = White,
    background = Black,
    onBackground = White,
    onBackgroundText = White,
    onBackgroundIconTint = White,
    secondary = Orange,
    text = White,
    description = Gray,
    selected = LightGreen,
    onSelected = Black,
    unSelected = LightOrange,
    onUnSelected = Black,
    checkBoxBorder = Orange,
    checkBoxChecked = Orange,
    onCheckBoxChecked = Black,
    searchTFBackground = Green,
    onSearchTFBackground = White,
    dialogBgColor = DarkerGray,
    drawerBgColor = DarkerGray

)
private val LightColors = lightColors(
    primary = Green,
    iconTint = Color.Black,
    background = White,
    onBackground = Black,
    onBackgroundText = Black,
    onBackgroundIconTint = Black,
    secondary = Orange,
    text = Color.Black,
    description = Gray,
    selected = LightGreen,
    onSelected = Black,
    unSelected = LightOrange,
    onUnSelected = Black,
    checkBoxBorder = Orange,
    checkBoxChecked = Orange,
    onCheckBoxChecked = Black,
    searchTFBackground = Green,
    onSearchTFBackground = White,
    dialogBgColor = White,
    drawerBgColor = White

)

val LocalSpaces = staticCompositionLocalOf { Spaces() }

val LocalColors = staticCompositionLocalOf { LightColors }

val LocalTypography = staticCompositionLocalOf { Typography() }

object KeySafeTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val spaces: Spaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current
}


@Composable
fun KeySafeTheme(
    spaces: Spaces = KeySafeTheme.spaces,
    typography: Typography = KeySafeTheme.typography,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val currentColors = remember { if (darkTheme) DarkColors else LightColors }
    val rememberedColors = remember { currentColors.copy() }.apply { updateColorsFrom(currentColors) }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Green.toArgb()
        }
    }


    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
    ) {
        ProvideTextStyle(typography.title, content = content)
    }

}