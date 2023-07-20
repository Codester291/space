package com.doye.space.dto.user

open class UserDTO (
        val firstname: String,
        val lastname: String,
        val username: String,
        val password: String,
        val clearance: String
) {
    constructor() : this("", "", "", "", "")
}