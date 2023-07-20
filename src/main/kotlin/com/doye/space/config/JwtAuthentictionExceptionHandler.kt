package com.doye.space.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class JwtAuthentictionExceptionHandler : AuthenticationEntryPoint, Serializable {
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        val objectMapper = ObjectMapper()
        
        val responseObject: MutableMap<String, String?> = mutableMapOf("responseCode" to "03", "responseMessage" to authException?.message)
        
        val responseMsg = objectMapper.writeValueAsString(responseObject)
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.writer?.write(responseMsg)
        response?.sendError(HttpServletResponse.SC_FORBIDDEN, responseMsg)
    }

    
}