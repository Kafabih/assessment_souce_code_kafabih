package app.svck.githubuserapp.domain.usecase

import app.svck.githubuserapp.data.model.request.User
import app.svck.githubuserapp.data.repository.UserRepository

class GetCachedUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> {
        return try {
            Result.success(userRepository.getAllCachedUsers())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
