package com.doye.space.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime
import java.util.UUID

class Sector (
    @MongoId private val id: String,
    private val uuid: UUID,
    private val name: String,
    @DBRef private val planets: List<Planet>?,
    
    @CreatedDate private val createdAt: LocalDateTime
) {
    constructor() : this("", UUID.randomUUID(), "", null, LocalDateTime.now())
}