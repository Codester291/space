package com.doye.space.dao

import com.doye.space.model.Person
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.UUID

interface PersonDao : ReactiveMongoRepository<Person, UUID> 