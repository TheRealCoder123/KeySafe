package com.nextsolutions.keyysafe.common.AppLaucnher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent

object OtherAppLauncher {

    const val MY_EMAIL = "upnext210@gmail.com"

    fun Context.sendMail(to: String, subject: String) : AppLauncherResult {
        return try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "vnd.android.cursor.item/email"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            startActivity(intent)
            AppLauncherResult.APP_FOUND
        } catch (e: ActivityNotFoundException) {
            AppLauncherResult.NO_APP
        } catch (t: Throwable) {
            AppLauncherResult.OTHER_ERROR
        }
    }


}