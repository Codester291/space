package com.doye.space.service

import com.doye.space.config.JwtService
import com.doye.space.dao.UserDao
import com.doye.space.dto.ResponseDTO
import com.doye.space.dto.user.UserDTO
import com.doye.space.dto.user.AuthDTO
import com.doye.space.model.User
import org.apache.juli.logging.Log
import org.apache.juli.logging.LogFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
        private val userDao: UserDao,
        private val jwtService: JwtService,
        private val passwordEncoder: PasswordEncoder,
        private val authenticationManager: AuthenticationManager
) {

    val logger: Log = LogFactory.getLog(AuthService::class.java)
    fun addUser(userDTO: UserDTO): User? {
        try {
            val user = User().mapUser(userDTO)
            user.secretPassword = passwordEncoder.encode(userDTO.password)

            return userDao.save(user).block()
        } catch (e: Exception) {
            logger.info("Error occurred in addUser: cause: [${e.message}]")
        }
        return null
    }

    fun auth(authDTO: AuthDTO): ResponseEntity<ResponseDTO> {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                authDTO.nickname,
                authDTO.secretPassword
        ))

        val user = userDao.findByNickname(authDTO.nickname)
            .switchIfEmpty(Mono.error(UsernameNotFoundException("Species not found")))
            .block()
     
        val token = user?.let { jwtService.generateToken(it) }
        return ResponseEntity.ok(ResponseDTO("00", "Successful", mapOf("token" to token)))
    }
}