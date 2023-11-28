package com.nextsolutions.keyysafe.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.autofill.AutofillManager
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object Other {
    fun formatTimestamp(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - timestamp

        // If the item was created less than a day ago, show the time
        return if (elapsedTime < 24 * 60 * 60 * 1000) {
            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            sdf.format(Date(timestamp))
        } else {
            // If more than a day has passed, show the date in "MMM dd"
            val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }

    fun getStartOfDayTimeStamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.timeInMillis
    }

    fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128) // You can choose the key size (128, 192, or 256 bits)
        return keyGenerator.generateKey()
    }

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getMimeType(fileUri: Uri): String? {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString())
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
    }


    fun Context.getFileFromContentUri(uri: Uri): File? {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val tempFile = File(cacheDir, "temp_file")
                tempFile.deleteOnExit()
                val outputStream = FileOutputStream(tempFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                return tempFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun Context.isAppSetAsAutofillService(): Boolean {

        val autofillManager = getSystemService(AutofillManager::class.java) as AutofillManager
        return autofillManager.hasEnabledAutofillServices()

//        val autofillIntent = Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
//        val resolveInfo = packageManager.resolveActivity(autofillIntent, PackageManager.MATCH_DEFAULT_ONLY)
//
//        if (resolveInfo != null) {
//            val packageName = packageName
//            val autofillServicePackageName = resolveInfo.activityInfo.packageName
//            return packageName == autofillServicePackageName
//        }
//        return false
    }

}