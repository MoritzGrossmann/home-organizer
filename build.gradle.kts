plugins {
  base

  // https://start.spring.io/
  id("org.springframework.boot") version "2.6.2" apply false
  id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false

  // https://kotlinlang.org/docs/releases.html#release-details
  kotlin("jvm") version "1.6.10" apply false
  kotlin("kapt") version "1.6.10" apply false
  kotlin("plugin.spring") version "1.6.10" apply false
  kotlin("plugin.jpa") version "1.6.10" apply false
}

//
// -- determine version from git --
//

fun determineVersionFromGit(): String {
  val commandOutput = java.io.ByteArrayOutputStream()

  return commandOutput.toString().trim().replace(Regex("^v"), "")
}

var determinedVersion = determineVersionFromGit()

val printVersion by tasks.creating {
  group = "help"
  description = "Prints the current version as calculated by determineVersionFromGit()."

  doLast {
    println(determinedVersion)
  }
}

allprojects {
  group = "com.grossmann.apps.homeorganizer"
  version = determinedVersion
}
