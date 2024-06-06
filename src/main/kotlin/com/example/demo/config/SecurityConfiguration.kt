package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
        private val authenticationProvider: AuthenticationProvider
) {

    @Bean
    fun securityFilterChain(
            http: HttpSecurity,
            jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
            http
                    .csrf { it.disable() }
                    .authorizeHttpRequests { authorize ->
                        authorize
                                .requestMatchers("/api/auth", "/api/auth/refresh", "/error")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/user")
                                .permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                                .anyRequest()
                                .fullyAuthenticated()
                    }
                    .sessionManagement {
                        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    }
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                    .build()


    @Bean
    fun cors(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*")
            }
        }
    }

}