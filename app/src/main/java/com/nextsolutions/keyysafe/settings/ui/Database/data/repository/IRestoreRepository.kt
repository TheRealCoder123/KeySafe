package com.nextsolutions.keyysafe.settings.ui.Database.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.google.gson.Gson
import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackupData
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.RestoreResponse
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.RestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IRestoreRepository @Inject constructor(
    private val context: Context,
    private val dao: DataAccessObject
) : RestoreRepository {
    override suspend fun restore(fileUri: Uri, filePass: String): RestoreResponse {
        return withContext(Dispatchers.IO){
             try {
                val inputStream = context.contentResolver.openInputStream(fileUri)

                val fileContent = fileUri.toFile().readBytes()

                val jsonData = decryptWithPassword(fileContent, filePass)

                val gson = Gson()
                val backupData = gson.fromJson(jsonData, BackupData::class.java)

                inputStream?.close()

                Log.e("backup data", "$backupData")

                RestoreResponse(true, backupData = backupData)
            } catch (e: Exception) {
                e.printStackTrace()
                RestoreResponse(false, e.localizedMessage ?: "Unknown error")
            }
        }
    }

    override suspend fun saveDataInDatabase(backUpData: BackupData) {
        dao.restoreBackUp(backUpData.entries, backUpData.labels)
    }

}

