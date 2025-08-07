package app.svck.githubuserapp.ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.svck.githubuserapp.data.model.request.User
import app.svck.githubuserapp.domain.usecase.GetCachedUsersUseCase
import app.svck.githubuserapp.domain.usecase.GetUserDetailsUseCase
import app.svck.githubuserapp.domain.usecase.SearchUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserSearchUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val canLoadMore: Boolean = true,
    val query: String = ""
)

data class UserDetailsUiState(
    val userDetails: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class UserSearchViewModel(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getCachedUsersUseCase: GetCachedUsersUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow(UserSearchUiState())
    val searchState: StateFlow<UserSearchUiState> = _searchState.asStateFlow()

    private val _detailsState = MutableStateFlow(UserDetailsUiState())
    val detailsState: StateFlow<UserDetailsUiState> = _detailsState.asStateFlow()

    private var currentPage = 1

    init {
        // Load initial users from cache when the ViewModel is created
        loadInitialUsers()
    }

    private fun loadInitialUsers() {
        viewModelScope.launch {
            _searchState.update { it.copy(isLoading = true) }
            getCachedUsersUseCase()
                .onSuccess { users ->
                    _searchState.update { it.copy(isLoading = false, users = users) }
                }
                .onFailure { error ->
                    _searchState.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun searchUsers(query: String) {
        if (query.isBlank()) return

        if (_searchState.value.query != query) {
            _searchState.value = UserSearchUiState(query = query)
            currentPage = 1
        }
        fetchUsers(isNewSearch = true)
    }

    fun loadMoreUsers() {
        if (_searchState.value.isLoadingMore || !_searchState.value.canLoadMore) return
        fetchUsers(isNewSearch = false)
    }

    private fun fetchUsers(isNewSearch: Boolean) {
        viewModelScope.launch {
            if (isNewSearch) {
                _searchState.update { it.copy(isLoading = true, error = null) }
            } else {
                _searchState.update { it.copy(isLoadingMore = true) }
            }

            searchUsersUseCase(_searchState.value.query, currentPage)
                .onSuccess { newUsers ->
                    _searchState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            users = if (isNewSearch) newUsers else currentState.users + newUsers,
                            canLoadMore = newUsers.isNotEmpty()
                        )
                    }
                    if (newUsers.isNotEmpty()) {
                        currentPage++
                    }
                }
                .onFailure { error ->
                    _searchState.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            error = "Failed to search users: ${error.message}"
                        )
                    }
                }
        }
    }

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            _detailsState.update { it.copy(isLoading = true, error = null) }
            getUserDetailsUseCase(username)
                .onSuccess { user ->
                    _detailsState.update { it.copy(isLoading = false, userDetails = user) }
                }
                .onFailure { error ->
                    _detailsState.update { it.copy(isLoading = false, error = "Failed to get details: ${error.message}") }
                }
        }
    }
}