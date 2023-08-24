package com.nextsolutions.keyysafe.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class Colors(
    primary: Color,
    iconTint: Color,
    background: Color,
    onBackground: Color,
    onBackgroundText: Color,
    onBackgroundIconTint: Color,
    secondary: Color,
    text: Color,
    description: Color,
    selected: Color,
    onSelected: Color,
    unSelected: Color,
    onUnSelected: Color,
    checkBoxBorder: Color,
    checkBoxChecked: Color,
    onCheckBoxChecked: Color,
    searchTFBackground: Color,
    onSearchTFBackground: Color,
    dialogBgColor: Color,
    drawerBgColor: Color
) {

    var primary by mutableStateOf(primary)
        private set

    var iconTint by mutableStateOf(iconTint)
        private set

    var onBackground by mutableStateOf(onBackground)
        private set

    var background by mutableStateOf(background)
        private set

    var onBackgroundText by mutableStateOf(onBackgroundText)
        private set

    var onBackgroundIconTint by mutableStateOf(onBackgroundIconTint)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var text by mutableStateOf(text)
        private set

    var description by mutableStateOf(description)
        private set

    var selected by mutableStateOf(selected)
        private set

    var onSelected by mutableStateOf(onSelected)
        private set

    var unSelected by mutableStateOf(unSelected)
        private set

    var onUnSelected by mutableStateOf(onUnSelected)
        private set

    var checkBoxBorder by mutableStateOf(checkBoxBorder)
        private set

    var checkBoxChecked by mutableStateOf(checkBoxChecked)
        private set

    var onCheckBoxChecked by mutableStateOf(onCheckBoxChecked)
        private set

    var searchTFBackground by mutableStateOf(searchTFBackground)
        private set

    var onSearchTFBackground by mutableStateOf(onSearchTFBackground)
        private set


    var dialogBgColor by mutableStateOf(dialogBgColor)
        private set

    var drawerBgColor by mutableStateOf(drawerBgColor)
        private set


    fun copy(
        primary: Color = this.primary,
        iconTint: Color = this.iconTint,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        onBackgroundText: Color = this.onBackgroundText,
        onBackgroundIconTint: Color = this.onBackgroundIconTint,
        secondary: Color = this.secondary,
        text: Color = this.text,
        description: Color = this.description,
        selected: Color = this.selected,
        onSelected: Color = this.onSelected,
        unSelected: Color = this.unSelected,
        onUnSelected: Color = this.onUnSelected,
        checkBoxBorder: Color = this.checkBoxBorder,
        checkBoxChecked: Color = this.checkBoxChecked,
        onCheckBoxChecked: Color = this.onCheckBoxChecked,
        searchTFBackground: Color = this.searchTFBackground,
        onSearchTFBackground: Color = this.onSearchTFBackground,
        dialogBgColor: Color = this.dialogBgColor,
        drawerBgColor: Color = this.drawerBgColor
    ) = Colors(
        primary = primary,
        iconTint = iconTint,
        background = background,
        onBackground = onBackground,
        onBackgroundText = onBackgroundText,
        onBackgroundIconTint = onBackgroundIconTint,
        secondary = secondary,
        text = text,
        description = description,
        selected = selected,
        onSelected = onSelected,
        unSelected = unSelected,
        onUnSelected = onUnSelected,
        checkBoxBorder = checkBoxBorder,
        checkBoxChecked = checkBoxChecked,
        onCheckBoxChecked = onCheckBoxChecked,
        searchTFBackground = searchTFBackground,
        onSearchTFBackground = onSearchTFBackground,
        dialogBgColor = dialogBgColor,
        drawerBgColor = drawerBgColor
    )

    fun updateColorsFrom(other: Colors) {
        primary = other.primary
        iconTint = other.iconTint
        background = other.background
        onBackground = other.onBackground
        onBackgroundText = other.onBackgroundText
        onBackgroundIconTint = other.onBackgroundIconTint
        secondary = other.secondary
        text = other.text
        description = other.description
        selected = other.selected
        onSelected = other.onSelected
        unSelected = other.unSelected
        onUnSelected = other.onUnSelected
        checkBoxBorder = other.checkBoxBorder
        checkBoxChecked = other.checkBoxChecked
        onCheckBoxChecked = other.onCheckBoxChecked
        searchTFBackground = other.searchTFBackground
        onSearchTFBackground = other.onSearchTFBackground
    }

}


fun lightColors(
    primary: Color,
    iconTint: Color,
    background: Color,
    onBackground: Color,
    onBackgroundText: Color,
    onBackgroundIconTint: Color,
    secondary: Color,
    text: Color,
    description: Color,
    selected: Color,
    onSelected: Color,
    unSelected: Color,
    onUnSelected: Color,
    checkBoxBorder: Color,
    checkBoxChecked: Color,
    onCheckBoxChecked: Color,
    searchTFBackground: Color,
    onSearchTFBackground: Color,
    dialogBgColor: Color,
    drawerBgColor: Color
) = Colors(
    primary,
    iconTint,
    background,
    onBackground,
    onBackgroundText,
    onBackgroundIconTint,
    secondary,
    text,
    description,
    selected,
    onSelected,
    unSelected,
    onUnSelected,
    checkBoxBorder,
    checkBoxChecked,
    onCheckBoxChecked,
    searchTFBackground,
    onSearchTFBackground,
    dialogBgColor,
    drawerBgColor
)

fun darkColors(
    primary: Color,
    iconTint: Color,
    background: Color,
    onBackground: Color,
    onBackgroundText: Color,
    onBackgroundIconTint: Color,
    secondary: Color,
    text: Color,
    description: Color,
    selected: Color,
    onSelected: Color,
    unSelected: Color,
    onUnSelected: Color,
    checkBoxBorder: Color,
    checkBoxChecked: Color,
    onCheckBoxChecked: Color,
    searchTFBackground: Color,
    onSearchTFBackground: Color,
    dialogBgColor: Color,
    drawerBgColor: Color
) = Colors(
    primary,
    iconTint,
    background,
    onBackground,
    onBackgroundText,
    onBackgroundIconTint,
    secondary,
    text,
    description,
    selected,
    onSelected,
    unSelected,
    onUnSelected,
    checkBoxBorder,
    checkBoxChecked,
    onCheckBoxChecked,
    searchTFBackground,
    onSearchTFBackground,
    dialogBgColor,
    drawerBgColor
)