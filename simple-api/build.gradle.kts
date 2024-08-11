dependencies {
    implementation(project(":simple-domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // JWT
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    //Redis
    implementation ("org.springframework.boot:spring-boot-starter-data-redis")

    //lettuce
    implementation("io.lettuce:lettuce-core:6.3.2.RELEASE")

    //mail
    implementation("org.springframework.boot:spring-boot-starter-mail:3.3.0")

    //properties
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    //thymeleaf
    implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")

    //OAuth2
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")
}
