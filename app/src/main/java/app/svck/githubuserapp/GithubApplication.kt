package app.svck.githubuserapp

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import app.svck.githubuserapp.di.appModule
import app.svck.githubuserapp.worker.CacheUpdateWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GithubApplication)
            modules(appModule)
        }

        scheduleCacheUpdate()
    }

    private fun scheduleCacheUpdate() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<CacheUpdateWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "cache-update-work",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}
