package com.doye.space.config

//import com.doye.space.enum.Clearance
import com.doye.space.enum.Clearance
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider
) {
    
    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        http.csrf { csrf ->
            csrf.disable()
        }.authorizeHttpRequests { requests ->
            requests.requestMatchers("/auth").permitAll()
            requests.requestMatchers("/entity/create").hasRole(Clearance.ONEABOVEALL.name)
            requests.requestMatchers("/entity/celestial/**").hasRole(Clearance.ONEABOVEALL.name)
            requests.requestMatchers("/entity/all").hasRole(Clearance.ONEABOVEALL.name)
            requests.anyRequest().authenticated()
        }.exceptionHandling{ exceptionHandling ->
            exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint())
        }.sessionManagement{ management ->
            management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        
        return http.build()
    }
    
    fun authenticationEntryPoint() : AuthenticationEntryPoint {
        return JwtAuthentictionExceptionHandler()
    }
}