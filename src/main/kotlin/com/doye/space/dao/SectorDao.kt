package com.doye.space.dao

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface SectorDao : MongoRepository<SectorDao, UUID>