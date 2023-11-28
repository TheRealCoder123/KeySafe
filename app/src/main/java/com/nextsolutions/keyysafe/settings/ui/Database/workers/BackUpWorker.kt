package com.nextsolutions.keyysafe.settings.ui.Database.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.BackUpRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject


@HiltWorker
class BackUpWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(
    context,
    workerParameters
) {

    @Inject
    lateinit var backUpRepository: BackUpRepository

    companion object {
        const val SUCCESS_BACK_UP = "SUCCESS_BACK_UP"
        const val ERROR_TEXT_BACK_UP = "ERROR_TEXT_BACK_UP"
    }


    override suspend fun doWork(): Result {
//        val backUpResponse = backUpRepository.export(Other.generateSecretKey(), "test.txt")
//        return if (backUpResponse.success){
//            val outputData = Data.Builder()
//                .putBoolean(SUCCESS_BACK_UP, true)
//                .putString(ERROR_TEXT_BACK_UP, backUpResponse.error)
//                .build()
//            Result.success(outputData)
//        }else{
//            val outputData = Data.Builder()
//                .putBoolean(SUCCESS_BACK_UP, false)
//                .putString(ERROR_TEXT_BACK_UP, backUpResponse.error)
//                .build()
//            Result.failure(outputData)
//        }
        return Result.failure()

    }
}