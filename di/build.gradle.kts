plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.demo.di"
    compileSdk = 36

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // Project modules
    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))

    // AndroidX / Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Hilt
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    // Chucker (debug + variant-specific no-op for release)
    debugImplementation(libs.chucker.library)

    implementation(libs.chucker.library)
    implementation(libs.chucker.library)
    implementation(libs.chucker.library)
    implementation(libs.chucker.library)

    implementation(libs.chucker.library.no.op)
    implementation(libs.chucker.library.no.op)
    implementation(libs.chucker.library.no.op)
    implementation(libs.chucker.library.no.op)

    // Room (if DI module needs it)
    implementation(libs.room.core)

    // Networking / Converters
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2.converter.scalars)

    // Media
    implementation(libs.vlc)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}