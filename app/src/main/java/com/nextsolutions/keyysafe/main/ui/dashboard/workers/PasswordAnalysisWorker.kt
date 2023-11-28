package com.nextsolutions.keyysafe.main.ui.dashboard.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PasswordAnalysisWorker@AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(
    context,
    workerParameters
){

    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }

}