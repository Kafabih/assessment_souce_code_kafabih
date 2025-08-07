package app.svck.githubuserapp.domain.usecase

import app.svck.githubuserapp.data.model.request.User
import app.svck.githubuserapp.data.repository.UserRepository


class SearchUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(query: String, page: Int): Result<List<User>> {
        return try {
            if (query.isBlank()) {
                return Result.success(emptyList())
            }
            Result.success(userRepository.searchUsers(query, page))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
