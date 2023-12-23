plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("8.1.1")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.badlogicgames.gdx:gdx:1.12.1")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1")
    // implementation("com.badlogicgames.gdx:gdx-platform:1.12.1")
    implementation("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-desktop")
    implementation("com.badlogicgames.gdx-controllers:gdx-controllers-desktop:2.2.3")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "net.approachcircle.game.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}
