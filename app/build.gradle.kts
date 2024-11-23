plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Firebase 플러그인 추가
}

android {
    namespace = "com.example.manwon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.manwon"
        minSdk = 24
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        dataBinding = true
    }


    dependencies {
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }
}
dependencies {
    implementation ("com.google.firebase:firebase-database") // Firebase Realtime Database
    implementation ("com.google.firebase:firebase-auth")
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.naver.maps:map-sdk:3.19.1")
    implementation ("net.sourceforge.jexcelapi:jxl:2.6.12")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
}
