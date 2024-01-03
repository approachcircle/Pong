plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("8.1.1")
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.meteordev.org/releases")
        name = "meteorRepositoryReleases"
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.badlogicgames.gdx:gdx:1.12.1")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1")
    implementation("io.socket:socket.io-client:2.1.0")
    implementation("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-desktop")
    implementation("com.badlogicgames.gdx-controllers:gdx-controllers-desktop:2.2.3")
    implementation("meteordevelopment:discord-ipc:1.1")
    implementation("com.google.code.gson:gson:2.8.9")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "net.approachcircle.game.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}
