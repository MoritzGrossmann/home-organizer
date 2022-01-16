package com.grossmann.apps.homeorganizer.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke

@Configuration
class SecurityConfig {
  @Bean
  fun filterChain(http: ServerHttpSecurity) = http.invoke {
    // disable all security features for now

    formLogin {
      disable()
    }

    csrf {
      disable()
    }

    httpBasic {
      disable()
    }

    // allow complete unauthenticated access for now

    authorizeExchange {
      authorize(anyExchange, permitAll)
    }
  }
}
