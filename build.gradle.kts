import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

version = "1.6.0"

plugins {
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

tasks {
    register("uploadMod") {
        group = "upload"

        val fabrikmcAllProject = project(":${rootProject.name}-all")

        dependsOn(fabrikmcAllProject.tasks.named("uploadModrinth"))
        dependsOn(fabrikmcAllProject.tasks.named("curseforge"))
    }

    dokkaHtmlMultiModule {
        outputDirectory.set(projectDir.resolve("docs"))

        includes.from("dokka/includes/main.md")

        pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
            version = project.version.toString()
            olderVersionsDir = projectDir.resolve("dokka/old-builds")
        }

        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            customStyleSheets = listOf(*(rootDir.resolve("dokka/stylesheets").listFiles() ?: emptyArray()))
        }
    }
}
