package com.doye.space.controller

import com.doye.space.dto.ResponseDTO
import com.doye.space.dto.person.PersonDTO
import com.doye.space.dto.user.UserDTO
import com.doye.space.model.Person
import com.doye.space.service.SpaceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.security.Principal

@RestController
class SpaceController(
        private val spaceService: SpaceService
) {
    
    //=== === === === === === === === ===
    // Entity
    //=== === === === === === === === ===
    @PostMapping("/entity/celestial/create")
    fun createCelestial(@RequestBody userDTO : UserDTO, principal: Principal) : ResponseDTO = spaceService.createCelestial(userDTO, principal)

    @PostMapping("/entity/create")
    fun create(@RequestBody personDTO: PersonDTO): ResponseDTO = spaceService.createEntity(personDTO)

    @GetMapping("/entity/all")
    fun getAllEntities(): Flux<Person> = spaceService.getAllEntities()
}