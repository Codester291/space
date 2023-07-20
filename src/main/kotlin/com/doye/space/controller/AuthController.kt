package com.doye.space.controller

import com.doye.space.dto.ResponseDTO
import com.doye.space.dto.user.AuthDTO
import com.doye.space.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    
    @PostMapping
    fun auth(@RequestBody authDTO: AuthDTO) : ResponseEntity<ResponseDTO> {
        return authService.auth(authDTO)
    }
}