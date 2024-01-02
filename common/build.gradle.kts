@file:OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("com.android.library")
}

group = "com.dasoops"
version = rootProject.version.toString()

// copy from OkHttp [https://github.com/square/okhttp/blob/master/okhttp/src/main/kotlinTemplates/okhttp3/internal/-InternalVersion.kt]
// Build & use com/dasoops/common/resources/-InternalVersion.kt
val copyKotlinTemplates = tasks.register<Copy>("copyKotlinTemplates") {
    from("src/commonMain/kotlinTemplates")
    into("$buildDir/generated/sources/kotlinTemplates")
    expand("projectVersion" to project.version)
    filteringCharset = Charsets.UTF_8.toString()
}

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(11)
    }
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir(copyKotlinTemplates.get().outputs)
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                api("io.github.oshai:kotlin-logging-jvm:5.1.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.5.1")
                api("androidx.core:core-ktx:1.9.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                api("org.slf4j:slf4j-api:1.7.36")
                api("ch.qos.logback:logback-classic:1.2.9")
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdkVersion(33)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(33)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
