package app.svck.githubuserapp.data.model.request

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val name: String?,
    val followers: Int?,
    val following: Int?,
    @Json(name = "public_repos")
    val publicRepos: Int?,
    val email: String?
)

