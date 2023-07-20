package com.doye.space.model

import com.doye.space.enum.WeatherCondition
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime
import java.util.UUID

class Planet(
    @MongoId private val id: String,
    private val uuid: UUID,
    private val name: String,
    private val weatherCondition: WeatherCondition,
    @DBRef private val sector: Sector?,
    @CreatedDate private val createdAt: LocalDateTime
) {
    constructor() : this("", UUID.randomUUID(), "", WeatherCondition.CLEAR, null, LocalDateTime.now())
}