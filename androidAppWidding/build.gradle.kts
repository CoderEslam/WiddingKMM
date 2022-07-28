plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.doubleclick.widdingkmm.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":sharedWidding"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.0")

    implementation("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")

    implementation("com.vanniktech:emoji:0.8.0")
    implementation("com.vanniktech:emoji-google:0.8.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.karumi:dexter:6.2.3")

}