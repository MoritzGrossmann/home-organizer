package com.grossmann.apps.homeorganizer

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application(
  private val buildInfo: BuildProperties,
  @Value("\${server.port}")
  private val port: Int
) {
  private val logger = KotlinLogging.logger {}

  @Bean
  fun run() = CommandLineRunner {
    logger.info { "launched version ${buildInfo.version} on http://localhost:${port}/" }
  }
}

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
