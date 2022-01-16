package com.grossmann.apps.homeorganizer

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.core.io.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ZeroCopyHttpOutputMessage
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Configuration
class SpaFallback {
  private val logger = KotlinLogging.logger {}

  @Bean
  @Order(-2)
  fun handler(
    @Value("classpath:/static/index.html") indexHtml: Resource
  ) = ErrorWebExceptionHandler { exchange, ex ->
    val request = exchange.request

    logger.trace { "path: ${request.path}\nuri: ${request.uri}\nheaders: ${request.headers}\nex: $ex" }

    when {
      // browser GET requests that don't start with /api
      request.method == HttpMethod.GET
        && request.headers.accept.contains(MediaType.TEXT_HTML)
        && !request.uri.path.startsWith("/api")
        && ex is ResponseStatusException && ex.status == HttpStatus.NOT_FOUND -> {
        val response = exchange.response

        response.statusCode = HttpStatus.OK
        response.headers.contentType = MediaType.TEXT_HTML

        (response as ZeroCopyHttpOutputMessage).writeWith(indexHtml.file, 0, indexHtml.contentLength())
      }
      else -> {
        // let other errors fall through
        Mono.error(ex)
      }
    }
  }
}
