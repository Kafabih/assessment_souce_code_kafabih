package app.svck.githubuserapp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.svck.githubuserapp.data.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CacheUpdateWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    // Inject repository using Koin
    private val userRepository: UserRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            userRepository.searchUsers("android", 1)
            Log.d("CacheUpdateWorker", "Successfully updated cache.")
            Result.success()
        } catch (e: Exception) {
            Log.e("CacheUpdateWorker", "Failed to update cache", e)
            Result.failure()
        }
    }
}
