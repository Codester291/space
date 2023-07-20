package com.doye.space.service

import com.doye.space.dao.PersonDao
import com.doye.space.dao.PlanetDao
import com.doye.space.dao.SectorDao
import com.doye.space.dao.UserDao
import com.doye.space.dto.ResponseDTO
import com.doye.space.dto.person.PersonDTO
import com.doye.space.dto.user.UserDTO
import com.doye.space.enum.Clearance
import com.doye.space.enum.Species
import com.doye.space.model.Person
import org.apache.juli.logging.Log
import org.apache.juli.logging.LogFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal
import java.util.*

@Service
class SpaceService (
   private val authService: AuthService,
   
   private val userDao: UserDao,
   private val personDao: PersonDao,
   private val planetDao: PlanetDao,
   private val sectorDao: SectorDao
) {
    
    val logger: Log = LogFactory.getLog(SpaceService::class.java)
    fun createCelestial(userDTO : UserDTO, principal: Principal) : ResponseDTO {
        logger.info("Principal: ${principal.name}")
        val accessUser = userDao.findByNickname(principal.name).block()
        if (accessUser != null && !accessUser.isClearance(Clearance.ONEABOVEALL)) {
           return ResponseDTO("ƒƒ", "Only One Above ALL")
        }
        
        val user = authService.addUser(userDTO)
        return ResponseDTO("00", "Created Celestial Successfully", user)
    }
    fun createEntity(personDto : PersonDTO) : ResponseDTO {
        val user = authService.addUser(personDto)
        val person = Person()
        
        person.uuid = UUID.randomUUID()
        person.species = Species.valueOf(personDto.species)
        
        if (user != null) {
            person.user = user
        }
        
        return ResponseDTO("00", "Success", personDao.save(person).block())
    }
    
    fun getAllEntities() : Flux<Person> = personDao.findAll().cache()
    
    
}