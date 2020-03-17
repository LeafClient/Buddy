plugins {
    java
    `maven-publish`
}

group = "com.leafclient"
version = "1.0.0"
description = "A customizable EventManager that avoids reflection usage"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts {
    archives(sourcesJar)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}