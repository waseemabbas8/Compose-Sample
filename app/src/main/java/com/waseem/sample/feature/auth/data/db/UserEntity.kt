package com.waseem.sample.feature.auth.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.waseem.sample.feature.auth.domain.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val name: String?
)

fun UserEntity.toUser() = User(id = id, email = email, name = name)

object UserEntityMapper {
    fun fromUser(user: User) = UserEntity(id = user.id, email = user.email, name = user.name)
}
