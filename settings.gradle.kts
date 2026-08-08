pluginManagement {
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
    }
}

rootProject.name = "EnterpriseComposeTemplate"

include(
    ":app",
    ":core:common",
    ":core:model",
    ":core:network",
    ":core:database",
    ":core:data",
    ":core:domain",
    ":core:designsystem",
    ":feature:home",
    ":feature:article",
    ":feature:profile",
)

