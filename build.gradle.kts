plugins {
    id("java")
}

group = "top.xinsin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("commons-io:commons-io:2.13.0")
}

tasks.test {
    useJUnitPlatform()
}