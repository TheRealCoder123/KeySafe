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
import com.nextsolutions.keyysafe.common.util.AppTheme
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
    selected = Color(0xFF4A554D),
    onSelected = Black,
    unSelected = LightOrange,
    onUnSelected = Black,
    checkBoxBorder = Orange,
    checkBoxChecked = Orange,
    onCheckBoxChecked = Black,
    searchTFBackground = BlackLighter,
    onSearchTFBackground = White,
    dialogBgColor = Color(0xFF3C413B),
    drawerBgColor = Color(0xFF3C413B)

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
    selected = Color(0xFFD2E8D3),
    onSelected = Black,
    unSelected = LightOrange,
    onUnSelected = Black,
    checkBoxBorder = Orange,
    checkBoxChecked = Orange,
    onCheckBoxChecked = Black,
    searchTFBackground = GrayLighter,
    onSearchTFBackground = Color.Black,
    dialogBgColor = Color(0xFFE9EEE8),
    drawerBgColor = Color(0xFFE9EEE8)
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
    appTheme: AppTheme,
    showNavBarColor: Boolean,
    content: @Composable () -> Unit,
) {

    val isDarkTheme = isSystemInDarkTheme()

    val currentColors = when(appTheme){
        AppTheme.System -> {
            if (isDarkTheme) DarkColors else LightColors
        }
        AppTheme.Dark -> {
            DarkColors
        }
        AppTheme.Light -> {
            LightColors
        }
    }

    val rememberedColors = remember { currentColors.copy() }.apply { updateColorsFrom(currentColors) }


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Green.toArgb()
            if (showNavBarColor){
                window.navigationBarColor = Green.toArgb()
            }else{
                when(appTheme){
                    AppTheme.System -> {
                        window.navigationBarColor = if (isDarkTheme) Black.toArgb() else White.toArgb()
                    }
                    AppTheme.Dark -> {
                        window.navigationBarColor = Black.toArgb()
                    }
                    AppTheme.Light -> {
                        window.navigationBarColor = White.toArgb()
                    }
                }
            }
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