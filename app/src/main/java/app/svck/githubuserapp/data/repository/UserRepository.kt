package app.svck.githubuserapp.data.repository

import android.util.Log
import app.svck.githubuserapp.data.local.UserDao
import app.svck.githubuserapp.data.model.request.User
import app.svck.githubuserapp.data.remote.GithubApiService

class UserRepository(
    private val apiService: GithubApiService,
    private val userDao: UserDao
) {
    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    suspend fun searchUsers(query: String, page: Int): List<User> {
        try {
            val remoteUsers = apiService.searchUsers(query, page, NETWORK_PAGE_SIZE).items
            if (remoteUsers.isNotEmpty()) {
                userDao.insertUsers(remoteUsers)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to fetch users from network", e)
        }
        return apiService.searchUsers(query, page, NETWORK_PAGE_SIZE).items
    }

    suspend fun getAllCachedUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun getUserDetails(username: String): User? {
        val cachedUser = userDao.getUserDetails(username)
        if (cachedUser == null || cachedUser.name == null) {
            return try {
                val remoteUser = apiService.getUserDetails(username)
                userDao.insertUsers(listOf(remoteUser))
                userDao.getUserDetails(username)
            } catch (e: Exception) {
                Log.e("UserRepository", "Failed to fetch user details for $username", e)
                cachedUser
            }
        }
        return cachedUser
    }
}