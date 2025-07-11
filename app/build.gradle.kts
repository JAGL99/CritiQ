plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeargs)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.jupiter)
}

android {
    namespace = "com.jagl.critiq"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jagl.critiq"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.jagl.critiq.core.common.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }

    junitPlatform {
        instrumentationTests {
            integrityCheckEnabled = false
        }
    }
}

dependencies {
    implementation(libs.timber)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.coil.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.material3)

    implementation(libs.androidx.paging.common.ktx)

    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit.jupiter.api)
    implementation(libs.junit.jupiter.params)
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.hilt.android.testing)

    testImplementation(libs.turbine)
    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.mockk)
    testImplementation(libs.junit5.test.core)

    kspAndroidTest(libs.hilt.android.compiler)
    testRuntimeOnly(libs.junit.jupiter.engine)

    androidTestImplementation(libs.androidx.paging.testing)
    androidTestImplementation (libs.androidx.rules)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.core.ktx.test)
    androidTestImplementation(libs.assertk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

/**
 * Custom Gradle task
 */
tasks.register("detectTodos") {
    group = "verification"
    description = "Detecta comentarios TODO en el código fuente"

    doLast {
        val android = project.extensions.getByName("android") as com.android.build.gradle.BaseExtension
        val srcDirs = android.sourceSets["main"].java.srcDirs // Para Kotlin/Java

        val todos = mutableListOf<String>()

        srcDirs.forEach { dir ->
            if (dir.exists()) {
                dir.walk()
                    .filter { it.isFile && (it.extension == "kt" || it.extension == "java") }
                    .forEach { file ->
                        file.forEachLine { line ->
                            if (line.contains("TODO")) {
                                todos.add("${file.relativeTo(dir)}: $line")
                            }
                        }
                    }
            }
        }

        if (todos.isNotEmpty()) {
            println("\n=== TODOs encontrados (${todos.size}) ===")
            todos.forEach { println(it) }
            throw GradleException("Se encontraron ${todos.size} TODOs en el código. Por favor corrígelos.")
        } else {
            println("¡No se encontraron TODOs en el código!")
        }
    }
}