package com.nextsolutions.keyysafe.settings.ui.Database.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BACKUP_DATA_PASSWORD
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.BackUpRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject


@HiltWorker
class AutoBackupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(
    context,
    workerParameters
){


    @Inject
    lateinit var backUpRepository: BackUpRepository

    override suspend fun doWork(): Result {
        val backUpResponse = backUpRepository.export("Keysafe_Auto_Backup", BACKUP_DATA_PASSWORD)
        Log.e("backup response from auto backup", backUpResponse.toString())
        return Result.success()
    }

}