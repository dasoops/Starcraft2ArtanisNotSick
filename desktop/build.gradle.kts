import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.dasoops"
version = rootProject.version.toString()

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common"))
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}
compose.desktop {
    application {
        mainClass = "MainKt"
        buildTypes {
            release.proguard.isEnabled.set(false)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Exe, TargetFormat.Msi)
            packageName = "Starcraft2ArtanisNotSick"
            packageVersion = rootProject.version.toString()


            windows {
                this.packageVersion = rootProject.version.toString().removeSuffix("-beta")
                modules.add("java.naming")
                val iconsRoot = project.file("../common/src/desktopMain/resources/image/build")
                iconFile.set(iconsRoot.resolve("icon-windows.ico"))
                menuGroup = "Starcraft2ArtanisNotSick"
                console = true

            }
        }
    }
}
