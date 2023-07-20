package com.doye.space.dao

import com.doye.space.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.*

interface UserDao : ReactiveMongoRepository<User, UUID> {
    fun findByNickname(username: String) : Mono<User>
}