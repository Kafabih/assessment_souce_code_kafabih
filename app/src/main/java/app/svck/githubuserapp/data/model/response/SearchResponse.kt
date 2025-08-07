package app.svck.githubuserapp.data.model.response

import app.svck.githubuserapp.data.model.request.User

data class SearchResponse(
    val items: List<User>
)