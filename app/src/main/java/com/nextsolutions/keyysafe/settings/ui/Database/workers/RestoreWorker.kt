package com.nextsolutions.keyysafe.settings.ui.Database.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nextsolutions.keyysafe.settings.ui.Database.domain.repository.RestoreRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class RestoreWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(
    context,
    workerParameters
)  {

    @Inject
    lateinit var restoreRepository: RestoreRepository

    companion object {
        const val FILE_URI_PARAM = "FILE_URI_PARAM"
        const val ERROR = "ERROR"
        const val SUCCESS = "SUCCESS"
        const val TEXT = "TEXT"
    }

    override suspend fun doWork(): Result {
//
//        val fileUri = inputData.getString(FILE_URI_PARAM)
//        Log.e("backup data", "file uri: $fileUri")
//
//        return if (!fileUri.isNullOrEmpty()){
//            Log.e("backup data", "file uri 2: ${Uri.parse(fileUri)}")
//            val result = restoreRepository.restore(fileUri.toUri(), Other.generateSecretKey())
//            Log.e("backup data", "result: $result")
//            if (result.success){
//                val outputData = Data.Builder()
//                    .putBoolean(SUCCESS, true)
//                    .putString(TEXT, "Successfully restored data")
//                    .build()
//               Result.success(outputData)
//            }else{
//                val outputData = Data.Builder()
//                    .putBoolean(SUCCESS, false)
//                    .putString(ERROR, result.error)
//                    .build()
//                Result.failure(outputData)
//            }
//        }else{
//            val outputData = Data.Builder()
//                .putBoolean(SUCCESS, false)
//                .putString(ERROR, "Invalid file")
//                .build()
//        }
       return Result.failure()


    }


}