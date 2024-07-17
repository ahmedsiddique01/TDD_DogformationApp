plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt)
}

android {
    namespace = "com.tdd.dogformation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tdd.dogformation"
        minSdk = 23
        targetSdk = 34
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

    flavorDimensions += "version"

    productFlavors {
        create("dev") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://dog.ceo/api/\"")
        }
        create("prod") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://dog.ceo/api/\"")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.runner)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    androidTestImplementation(libs.androidx.navigation.testing)


    implementation(libs.androidx.core.splashscreen)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.room.runtime)

    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.glide)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.okhttp)
    api(libs.logging.interceptor)

    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockk)

    testImplementation(libs.kotest.assertions.core)
    testApi(libs.kotlin.test)
}