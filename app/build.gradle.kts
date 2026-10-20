
plugins {
    // Plugin para desarrollar aplicaciones Android.
    alias(libs.plugins.android.application)

    // Plugin para utilizar Kotlin con Jetpack Compose.
    alias(libs.plugins.kotlin.compose)
}

android {
    // Identificador del paquete de nuestra aplicación.
    namespace = "com.example.proyectoapisnico"

    // Versión del SDK utilizada para compilar.
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        // Identificador único de la aplicación.
        applicationId = "com.example.proyectoapisnico"

        // Versión mínima de Android compatible.
        minSdk = 24

        // Versión de Android objetivo.
        targetSdk = 37

        // Versión de la aplicación.
        versionCode = 1
        versionName = "1.0"

        // Configuración para ejecutar pruebas instrumentadas.
        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Configuración de optimización de la versión final.
            optimization {
                enable = true
                packageScope = setOf(
                    "androidx.**",
                    "kotlin.**",
                    "kotlinx.**"
                )
            }
        }
    }

    compileOptions {
        // Versión de Java utilizada para compilar.
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        // Habilita Jetpack Compose para construir la interfaz.
        compose = true
    }
}

dependencies {
    // Versiones compatibles de las bibliotecas de Compose.
    implementation(platform(libs.androidx.compose.bom))

    // Componentes básicos de una aplicación con Compose.
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)

    // Funcionalidades del ciclo de vida de Android.
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Navegación entre pantallas de la aplicación.
    implementation(libs.androidx.navigation.compose)

    // ViewModel y manejo del estado en Jetpack Compose.
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Dependencias para las pruebas.
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Herramientas de desarrollo y pruebas de Compose.
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
