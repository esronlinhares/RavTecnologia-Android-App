plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.ravtecnologia"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ravtecnologia"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true

    }
}

dependencies {
    val room_version = "2.8.1"

    // 🧠 Room (Banco de Dados)
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // 💬 Coroutines (para lidar com dados de forma assíncrona)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // 🎨 Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))

    // 🎨 Material 3 e ícones
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.compose.material:material-icons-extended")

    // 🧭 Navegação entre telas
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // 🧱 Compose básico
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // ✅ Testes
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // 📦 RecyclerView (caso precise em alguma tela XML)
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // Para testar Compose com JUnit4
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.2.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.0")

    // Coil para imagens
    implementation("io.coil-kt:coil-compose:2.4.0")

}