package com.nextsolutions.keyysafe.settings.ui.Appearance.Language

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import java.util.Locale


object LanguageConfig {

    const val SELECTED_LANGUAGE_KEY = "SELECTED_LANGUAGE_KEY"

    fun changeLanguage(context: Context, languageCode: String): ContextWrapper {
        var context = context
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        val systemLocale: Locale = configuration.locales.get(0)
        if (languageCode != "" || !systemLocale.language.equals(languageCode)) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            configuration.setLocale(locale)
            context = context.createConfigurationContext(configuration)
        }
        return ContextWrapper(context)
    }
}