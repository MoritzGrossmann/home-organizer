import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot")
  id("io.spring.dependency-management")

  kotlin("jvm")
  kotlin("kapt")
  kotlin("plugin.spring")
  kotlin("plugin.jpa")
}

repositories {
  mavenCentral()

  jcenter()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-webflux")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

  // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging-jvm
  implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

  // https://mvnrepository.com/artifact/com.github.kittinunf.fuel/fuel
  implementation("com.github.kittinunf.fuel:fuel:2.2.1")

  // https://mvnrepository.com/artifact/com.github.kittinunf.fuel/fuel-jackson
  implementation("com.github.kittinunf.fuel:fuel-jackson:2.3.1")

  // https://mvnrepository.com/artifact/com.github.kittinunf.result/result
  implementation("com.github.kittinunf.result:result:3.0.1")



  kapt("org.springframework.boot:spring-boot-configuration-processor")
  kapt("org.hibernate:hibernate-jpamodelgen")

  //runtimeOnly("com.h2database:h2")
  runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("org.springframework.security:spring-security-test")
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()

  systemProperties(
    "spring.profiles.active" to "gradleTest"
  )
}

springBoot {
  buildInfo()
}

val npmInstallWebapp by tasks.creating(Exec::class) {
  workingDir = file("$rootDir/frontend/app")

  inputs.file("$rootDir/frontend/app/package.json")
  inputs.file("$rootDir/frontend/app/yarn.lock")

  outputs.dir("$rootDir/frontend/app/node_modules")

  val isWindows = org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS)
  commandLine(
    if (isWindows) "yarn.cmd" else "yarn"
  )
}

val buildVueWebapp by tasks.creating(Exec::class) {
  group = "build"
  dependsOn(npmInstallWebapp)

  workingDir = file("$rootDir/frontend/app")

  inputs.dir("$rootDir/frontend/app/public")
  inputs.dir("$rootDir/frontend/app/src")
  inputs.file("$rootDir/frontend/app/package.json")
  inputs.file("$rootDir/frontend/app/yarn.lock")
  inputs.file("$rootDir/frontend/app/quasar.conf.js")

  outputs.dir("$rootDir/frontend/app/dist/spa")

  val isWindows = org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS)
  commandLine(
    if (isWindows) "yarn.cmd" else "yarn", "build"
  )
}

tasks.named<ProcessResources>("processResources") {
  from(buildVueWebapp) {
    into("static")
  }
}

val bootRunSkipVue by tasks.creating {
  group = "application"
  dependsOn("bootRun")
}

gradle.taskGraph.whenReady {
  if (hasTask(bootRunSkipVue)) {
    npmInstallWebapp.enabled = false
    buildVueWebapp.enabled = false
  }
}
