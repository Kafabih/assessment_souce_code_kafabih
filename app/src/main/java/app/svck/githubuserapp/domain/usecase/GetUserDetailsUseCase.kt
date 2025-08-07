package app.svck.githubuserapp.domain.usecase

import app.svck.githubuserapp.data.model.request.User
import app.svck.githubuserapp.data.repository.UserRepository

class GetUserDetailsUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): Result<User?> {
        return try {
            Result.success(userRepository.getUserDetails(username))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}