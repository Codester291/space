package com.doye.space.enum

import org.springframework.security.core.GrantedAuthority

enum class Clearance(private val displayName: String) : GrantedAuthority {
    ELITE("ELITE"),
    NOMAD("NOMAD"),
    EXPLORER("EXPLORER"),
    COMMONER("COMMONER"),
    ONEABOVEALL("ONEABOVEALL"),
    CELESTIAL("CELESTIAL"),
    FEDERATION("FEDERATION"),
    BOUNTYHUNTER("BOUNTYHUNTER"),;

    override fun getAuthority(): String {
        return displayName
    }
}