package com.nextsolutions.keyysafe.app.graphs

import com.nextsolutions.keyysafe.auth.domain.enums.AuthScreenUsability
import com.nextsolutions.keyysafe.common.password_manager.PasswordChecker
import com.nextsolutions.keyysafe.common.util.ArgumentKeys
import com.nextsolutions.keyysafe.common.util.Constants.NO_LABEL

sealed class MainNavigation(val route: String) {
    object AuthScreen : MainNavigation("auth_screen/{${ArgumentKeys.AUTH_SCREEN_USABILITY}}") {
        fun passScreenUsability(usability: AuthScreenUsability): String{
            return this.route.replace(
                oldValue = "{${ArgumentKeys.AUTH_SCREEN_USABILITY}}",
                newValue = usability.name
            )
        }
    }
    object Dashboard : MainNavigation("dashboard_screen")
    object AutoFill : MainNavigation("autofill_screen")
    object AnalyticsScreen : MainNavigation("analytics_screen")
    object TrashScreen : MainNavigation("trash_screen")
    object ArchiveScreen : MainNavigation("archive_screen")
    object CreateEntryScreen : MainNavigation("create_entry_screen/{${ArgumentKeys.ENTRY_ID_TO_EDIT_CREATE_ENTRY_SCREEN_KEY}}"){
        fun passEntryId(id: String?): String{
            return this.route.replace(
                oldValue = "{${ArgumentKeys.ENTRY_ID_TO_EDIT_CREATE_ENTRY_SCREEN_KEY}}",
                newValue = id.toString()
            )
        }
    }
    object EditLabelsScreen : MainNavigation("edit_labels_screen")
    object PasswordGeneratorScreen : MainNavigation("password_generator_screen/{${ArgumentKeys.SHOULD_PASS_GENERATED_PASSWORD_BACK}}"){

        fun passIfNeedsToSendBackResult(shouldSendResultBack: Boolean = false): String{
            return this.route.replace(
                oldValue = "{${ArgumentKeys.SHOULD_PASS_GENERATED_PASSWORD_BACK}}",
                newValue = shouldSendResultBack.toString()
            )
        }

    }
    object ViewAllEntriesScreen : MainNavigation("view_all_entries_screen/{${ArgumentKeys.LABEL_ID_TO_VIEW_ALL_ENTRIES_SCREEN}}"){

        const val VIEW_ALL_ENTRIES = -1

        fun passLabelId(id: Int): String{
            return this.route.replace(
                oldValue = "{${ArgumentKeys.LABEL_ID_TO_VIEW_ALL_ENTRIES_SCREEN}}",
                newValue = id.toString()
            )
        }
    }
    object ViewAllEntriesByPassStrengthScreen : MainNavigation("view_all_entries_by_pass_strength_screen/{${ArgumentKeys.PASSWORD_STRENGTH_TO_VIEW_ENTRIES_BY_PASSWORD_STRENGTH}}/{${ArgumentKeys.LABEL_ID_TO_VIEW_ENTRIES_BY_STRENGTH_SCREEN}}") {

        fun passPasswordStrength(strength: PasswordChecker.PasswordStrength): String{
            return this.route.replace(
                oldValue = "{${ArgumentKeys.PASSWORD_STRENGTH_TO_VIEW_ENTRIES_BY_PASSWORD_STRENGTH}}",
                newValue = strength.name
            )
        }

        fun passArgs(
            strength: PasswordChecker.PasswordStrength,
            labelId: Int = NO_LABEL
        ): String{
            return "view_all_entries_by_pass_strength_screen/${strength.name}/${labelId}"
        }

    }


}