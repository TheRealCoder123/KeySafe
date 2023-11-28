package com.nextsolutions.keyysafe.settings.ui.Database.data.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.google.gson.Gson
import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackUpResponse
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackupData
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.BackUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class IBackUpRepository @Inject constructor(
    private val dataAccessObject: DataAccessObject,
    private val context: Context
) : BackUpRepository {
    override suspend fun export(fileName: String, filePass: String) : BackUpResponse {
        return withContext(Dispatchers.IO){
            val backupData = getBackUpData()
            val gson = Gson()
            val jsonData = gson.toJson(backupData)

            try {
                val dir = File(Environment.getExternalStorageDirectory(), "KeySafeBackup")
                //val dir = File(context.getExternalFilesDir(null), "backup")

                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val backupFile = File(dir, "$fileName.enc")

                val encryptedData = encrypt(jsonData, filePass)
                backupFile.writeBytes(encryptedData)

                backupFile.createNewFile()
                BackUpResponse(true, Uri.fromFile(backupFile).toString())
            } catch (e: Exception) {
                BackUpResponse(false, e.localizedMessage ?: "Unknown error")
            }
        }

    }

    override suspend fun getBackUpData(): BackupData {
        val entries = dataAccessObject.getAllEntriesWithoutFlow()
        val labels = dataAccessObject.getAllLabelsWithoutFlow()
        return BackupData(entries, labels)
    }
}