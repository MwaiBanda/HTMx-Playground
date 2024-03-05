package data

import domain.model.Post
import domain.repository.PostRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PostRepositoryImpl(
    private val httpClient: HttpClient
): PostRepository {
    override suspend fun getPosts(): List<Post> {
        return httpClient.get("https://jsonplaceholder.typicode.com/posts").body()
    }
}