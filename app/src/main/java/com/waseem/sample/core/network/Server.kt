package com.waseem.sample.core.network

sealed class Server(val uri: String, val port: Int?) {
    data object Local : Server(uri = "192.168.17.1", port = 8080)
    data object Staging : Server(uri = "run.mocky.io", port = null)
    data object Production: Server(uri = "api.sample.com", port = null)
}