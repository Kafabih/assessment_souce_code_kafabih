package app.svck.githubuserapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.svck.githubuserapp.data.model.request.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query("SELECT * FROM users WHERE login LIKE '%' || :query || '%'")
    suspend fun searchUsers(query: String): List<User>

    @Query("SELECT * FROM users WHERE login = :username")
    suspend fun getUserDetails(username: String): User?

    // New function to get all users from the table
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}