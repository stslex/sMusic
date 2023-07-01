import java.net.URI

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = URI("https://jitpack.io/")
        }
    }
}

rootProject.name = "sMusic"
include(":app")
include(":core:ui")
include(":core:network")
include(":core:datastore")
include(":core:player")
include(":core:navigation")

include(":feature:settings")
include(":feature:recommendation")
include(":feature:search")
include(":feature:favourite")
include(":core:database")
include(":feature:player")
