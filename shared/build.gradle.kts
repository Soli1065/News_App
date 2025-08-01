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
            implementation("io.ktor:ktor-client-core:2.3.0")
            implementation("io.ktor:ktor-client-json:2.3.0")
            implementation("io.ktor:ktor-client-serialization:2.3.0")
            implementation("io.ktor:ktor-client-logging:2.3.0")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
            implementation("com.russhwolf:multiplatform-settings-test:1.1.1")
            implementation("org.slf4j:slf4j-nop:2.0.9")
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:2.3.0")
            implementation("com.russhwolf:multiplatform-settings-datastore:1.1.1")
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("androidx.preference:preference-ktx:1.2.1")
//            implementation("com.russhwolf:multiplatform-settings-shared-preferences:1.1.1")
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