pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.X.X" apply false
        id("org.jetbrains.kotlin.android") version "1.9.X" apply false
        id("com.google.gms.google-services") version "4.4.1" apply false
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ParcialFinal"
include(":app")
 