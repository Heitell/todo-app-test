package org.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RestClient(private val urlPath: String) {

    private val client = HttpClient(CIO) {
        install(Logging)
    }

    private val clientWithAuth = HttpClient(CIO) {
        install(Logging)
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = "admin", password = "admin")
                }
            }
        }
    }

    fun delete(id: ULong) : HttpResponse {
        return runBlocking {
            clientWithAuth.request("$urlPath/$id") {
                method = HttpMethod.Delete
                headers.append("Content-Type", "application/json")
            }
        }
    }

    fun post(item: TodoItem) : HttpResponse {
        return runBlocking {
            client.request(urlPath) {
                method = HttpMethod.Post
                setBody(Json.encodeToString(item))
                headers.append("Content-Type", "application/json")
            }
        }
    }

    fun get(offset: Int? = null, limit: Int? = null) : HttpResponse {
        return runBlocking {
            client.request(urlPath) {
                method = HttpMethod.Get
                headers.append("Content-Type", "application/json")
                offset?.let { parameter("offset", it) }
                limit?.let { parameter("limit", it) }
            }
        }
    }

    fun put(id: ULong, updatedItem: TodoItem) : HttpResponse {
        return runBlocking {
            client.request("$urlPath/$id") {
                method = HttpMethod.Put
                setBody(Json.encodeToString(updatedItem))
                headers.append("Content-Type", "application/json")
            }
        }
    }
}