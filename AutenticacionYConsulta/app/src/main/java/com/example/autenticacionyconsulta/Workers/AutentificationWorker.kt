package com.example.autenticacionyconsulta.Workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class AutentificationWorker(appContext: Context,
                             parameters: WorkerParameters
): Worker(appContext, parameters) {
    override fun doWork(): Result {
        try {
            return Result.success()
        }
        catch (e: Exception){
            return  Result.failure()
        }
    }
}