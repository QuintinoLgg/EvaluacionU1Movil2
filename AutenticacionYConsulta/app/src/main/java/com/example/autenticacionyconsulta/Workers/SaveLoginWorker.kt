package com.example.autenticacionyconsulta.Workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SaveLoginWorker (appContext: Context,
                       params: WorkerParameters
): Worker(appContext,params) {
    override fun doWork(): Result {
        try {
            return Result.success()
        }
        catch (e: Exception){
            return Result.failure()
        }
    }
}