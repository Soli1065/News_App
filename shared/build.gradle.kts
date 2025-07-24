import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation("io.ktor:ktor-client-core:2.3.0")
            implementation("io.ktor:ktor-client-json:2.3.0")
            implementation("io.ktor:ktor-client-serialization:2.3.0")
            implementation("io.ktor:ktor-client-logging:2.3.0")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:2.3.0")
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.0")
        }
    }
}

android {
    namespace = "com.example.newsapp_kmp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
