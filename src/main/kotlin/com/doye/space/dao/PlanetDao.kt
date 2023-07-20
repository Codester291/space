package com.doye.space.dao

import com.doye.space.model.Planet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.UUID

interface PlanetDao : ReactiveMongoRepository<Planet, UUID>