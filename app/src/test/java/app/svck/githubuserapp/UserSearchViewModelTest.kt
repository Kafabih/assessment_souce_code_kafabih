package app.svck.githubuserapp


import app.svck.githubuserapp.data.model.request.User
import app.svck.githubuserapp.domain.usecase.GetCachedUsersUseCase
import app.svck.githubuserapp.domain.usecase.GetUserDetailsUseCase
import app.svck.githubuserapp.domain.usecase.SearchUsersUseCase
import app.svck.githubuserapp.ui.presentation.viewmodel.UserSearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserSearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var searchUsersUseCase: SearchUsersUseCase
    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
    private lateinit var getCachedUsersUseCase: GetCachedUsersUseCase
    private lateinit var viewModel: UserSearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        searchUsersUseCase = mockk()
        getUserDetailsUseCase = mockk()
        getCachedUsersUseCase = mockk()

        // Mock the initial cache load to return an empty list
        coEvery { getCachedUsersUseCase() } returns Result.success(emptyList())

        viewModel = UserSearchViewModel(
            searchUsersUseCase,
            getUserDetailsUseCase,
            getCachedUsersUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchUsers success updates state with users`() = runTest {
        // Given
        val query = "android"
        val mockUsers = listOf(User(1, "android", "", "Android", 0, 0, 0, null))
        coEvery { searchUsersUseCase(query, 1) } returns Result.success(mockUsers)

        // When
        viewModel.searchUsers(query)
        testDispatcher.scheduler.advanceUntilIdle() // Execute coroutines

        // Then
        val state = viewModel.searchState.value
        assertEquals(false, state.isLoading)
        assertEquals(mockUsers, state.users)
        assertEquals(null, state.error)
    }

    @Test
    fun `searchUsers failure updates state with error`() = runTest {
        // Given
        val query = "android"
        val errorMessage = "Network Error"
        coEvery { searchUsersUseCase(query, 1) } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.searchUsers(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.searchState.value
        assertEquals(false, state.isLoading)
        assertEquals(true, state.users.isEmpty())
        assertEquals("Failed to search users: $errorMessage", state.error)
    }
}