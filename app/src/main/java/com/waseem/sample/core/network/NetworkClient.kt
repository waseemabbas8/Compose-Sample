package com.waseem.sample.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.DEFAULT_PORT
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NetworkClient(
    server: Server
) {
    val client = HttpClient(Android) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            val timeout = 300000L
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                }
            )
        }
        install(Resources)
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = server.uri
                port = server.port ?: DEFAULT_PORT
            }
        }
    }

    operator fun invoke(): HttpClient = client

    suspend inline fun <reified Resource: Any, reified ResponseType: Any> get(
        resource: Resource,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): Result<ResponseType> {
        val response = client.get(resource = resource,builder = builder)
        return if (response.status.isSuccess()) {
            Result.success(response.body())
        } else {
            Result.failure(Throwable("API call failed with status code ${response.status.value}"))
        }
    }
}