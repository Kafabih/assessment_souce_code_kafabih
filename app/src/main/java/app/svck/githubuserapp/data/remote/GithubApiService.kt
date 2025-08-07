package app.svck.githubuserapp.data.remote

import app.svck.githubuserapp.data.model.request.User
import app.svck.githubuserapp.data.model.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): User
}