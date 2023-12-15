package com.waseem.sample.feature.auth.fakes.data

import com.waseem.sample.feature.auth.domain.User
import java.util.UUID

private fun FakeUser(
    id: String = UUID.randomUUID().toString(),
    name: String = (0..2).random().let { fakeNames[it] },
    email: String = "your@example.com"
) = User(
    id = id,
    name = name,
    email = email
)

fun FakeUserList(): List<User> {
    val size = (1..3).random()
    return List(size) { FakeUser() }
}

private val fakeNames = listOf("Waseem", "John", "Angela")