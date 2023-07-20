package com.doye.space.model

import com.doye.space.dto.user.UserDTO
import com.doye.space.enum.Clearance
import jakarta.annotation.Generated
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

class User(
        @MongoId @Id @Generated var id: String,
        var firstname: String,
        var lastname: String,

        @Indexed(unique = true)
        var nickname: String,
        var secretPassword: String,

        var clearance: Clearance,

        @CreatedDate
        var createdAt: LocalDateTime,
) : UserDetails {

    constructor() : this("", "", "", "", "", Clearance.COMMONER, LocalDateTime.now())

    fun mapUser(userDto: UserDTO): User {
        this.firstname = userDto.firstname
        this.lastname = userDto.lastname
        this.nickname = userDto.username
        this.clearance = Clearance.valueOf(userDto.clearance)

        return this
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(clearance)
    }

    override fun getPassword(): String {
        return secretPassword
    }

    override fun getUsername(): String {
        return nickname
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun isClearance(clearance: Clearance): Boolean {
        return this.clearance == clearance
    }

}