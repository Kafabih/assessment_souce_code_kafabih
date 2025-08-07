package app.svck.githubuserapp.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import app.svck.githubuserapp.data.model.request.User

@Database(
    entities = [User::class],
    version = 2, // Increment back to version 2
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 1, to = 2) // Add the migration back
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}