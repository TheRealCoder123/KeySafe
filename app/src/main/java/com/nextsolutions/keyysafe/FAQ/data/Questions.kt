package com.nextsolutions.keyysafe.FAQ.data

import android.content.Context
import com.nextsolutions.keyysafe.FAQ.domain.model.FAQuestion
import com.nextsolutions.keyysafe.R

fun getFAQList(context: Context): List<FAQuestion> {
    return listOf(
        FAQuestion(
            1,
            context.getString(R.string.q_i_forgot_my_master_password),
            context.getString(R.string.a_if_you_ve_forgotten_your_master_password_don_t_worry_while_it_s_not_recoverable_you_can_restore_your_data_if_you_ve_saved_a_backup_file_make_use_of_your_backup_file_to_recover_your_precious_data)
        ),
        FAQuestion(
            2,
            context.getString(R.string.q_how_can_i_transfer_my_data_to_a_new_device),
            context.getString(R.string.a_we_ve_got_you_covered_our_app_offers_an_excellent_file_backup_system_with_encryption_you_can_back_up_all_your_entries_to_a_file_saved_on_your_local_phone_storage_then_transfer_this_file_to_your_new_device_and_easily_restore_your_data_from_it)
        ),
        FAQuestion(
            3,
            context.getString(R.string.q_how_can_i_recover_my_data_after_a_device_reset),
            context.getString(R.string.a_our_app_provides_a_robust_encrypted_file_backup_system_for_such_situations_you_can_save_all_your_entries_in_one_file_or_use_the_one_that_keysafe_automatically_creates_this_way_you_can_recover_your_data_on_the_same_device_or_on_another_one)
        ),
        FAQuestion(
            4,
            context.getString(R.string.q_is_there_an_automatic_backup_feature_in_keysafe),
            context.getString(R.string.a_yes_we_ve_got_your_back_keysafe_features_an_automatic_backup_system_that_creates_a_backup_of_all_your_entries_in_a_file_named_keysafe_auto_backup_enc_this_file_is_also_encrypted_and_the_app_updates_it_every_30_minutes_if_changes_are_made_you_can_also_choose_to_turn_this_feature_off)
        ),
        FAQuestion(
            5,
            context.getString(R.string.q_does_keysafe_have_a_password_generator),
            context.getString(R.string.a_absolutely_keysafe_comes_with_a_password_generator_you_can_find_it_in_the_menu_under_password_generator)
        ),
        FAQuestion(
            6,
            context.getString(R.string.q_tell_me_more_about_the_label_system_in_keysafe_how_does_it_work),
            context.getString(R.string.a_the_label_system_in_keysafe_is_simple_and_effective_you_can_assign_a_label_to_an_entry_for_example_you_can_create_a_label_called_personal_accounts_and_organize_all_your_personal_accounts_under_this_label)
        ),
        FAQuestion(
            7,
            context.getString(R.string.q_is_keysafe_available_in_multiple_languages),
            context.getString(R.string.a_yes_our_app_supports_multiple_languages_including_english_default_macedonian_german_portuguese_spanish_serbian_and_more_languages_are_on_the_way)
        ),
        FAQuestion(
            8,
            context.getString(R.string.q_what_is_the_purpose_of_the_trash_storage_in_keysafe_and_why_is_it_limited_to_150_entries),
            context.getString(R.string.a_the_purpose_of_limiting_the_trash_storage_to_150_entries_is_to_manage_space_efficiently_as_you_add_more_entries_to_the_trash_they_consume_storage_space_imagine_having_1000_entries_in_the_trash_that_would_be_quite_overwhelming_this_is_why_we_set_a_limit_but_you_can_always_delete_entries_from_the_trash_to_free_up_space)
        ),
        FAQuestion(
            9,
            context.getString(R.string.q_is_there_a_way_to_customize_or_change_the_limit_of_trash_storage_in_keysafe),
            context.getString(R.string.a_no_the_limit_of_trash_storage_is_not_customizable)
        ),
        FAQuestion(
            10,
            context.getString(R.string.q_how_do_i_change_my_master_password),
            context.getString(R.string.a_changing_your_master_password_is_easy_go_to_settings_security_change_master_password_to_update_it)
        ),
        FAQuestion(
            11,
            context.getString(R.string.q_how_can_i_access_my_archived_entries_in_keysafe),
            context.getString(R.string.a_accessing_your_archived_entries_is_a_breeze_simply_go_to_the_menu_and_click_on_archive_archived_entries)
        ),
        FAQuestion(
            12,
            context.getString(R.string.q_are_trashed_and_archived_entries_backed_up),
            context.getString(R.string.a_yes_all_entries_including_trashed_and_archived_ones_are_backed_up_in_the_backup_file_the_only_entries_not_backed_up_are_the_ones_you_ve_permanently_deleted)
        ),
        FAQuestion(
            13,
            context.getString(R.string.q_how_can_i_delete_entries_permanently_from_the_trash_storage_in_keysafe),
            context.getString(R.string.a_to_permanently_delete_entries_from_the_trash_go_to_the_trash_in_the_menu_then_select_all_trash_entries_you_ll_see_a_dialog_with_two_options_and_one_of_them_is_to_delete_the_entry_permanently)
        )
    )
}
