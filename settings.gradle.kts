pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://repo-api.modlabs.cc/repo/maven/maven-mirror/") {
            name = "Flawcra Mirrors"
        }
    }
}