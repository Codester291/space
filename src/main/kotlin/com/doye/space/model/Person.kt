package com.doye.space.model

import com.doye.space.enum.Species
import jakarta.annotation.Generated
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime
import java.util.UUID


class Person (
        @MongoId @Id @Generated private val id: String,
        var uuid: UUID,

        @DBRef var user: User,
        var species: Species,
        @DBRef var planet: Planet?,
        var spaceId: String?,

        @CreatedDate
    private val createdAt: LocalDateTime
) {
    constructor() : this("", UUID.randomUUID(), User(), Species.IWIN, Planet(), "", LocalDateTime.now())
}