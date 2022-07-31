plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")// دي صح
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
//    implementation("androidx.room:room-compiler:2.4.2")
//    implementation("androidx.room:room-ktx:2.4.2")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")

    implementation("com.vanniktech:emoji:0.8.0")
    implementation("com.vanniktech:emoji-google:0.8.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.karumi:dexter:6.2.3")

    implementation("com.squareup.picasso:picasso:2.8")

    implementation("com.google.firebase:firebase-database-ktx:20.0.5")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.6")
    implementation("com.google.firebase:firebase-firestore-ktx:24.2.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.0.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.0.6")
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx:20.1.2")


    // ExoPlayer
    api("com.google.android.exoplayer:exoplayer-core:2.17.1")
    api("com.google.android.exoplayer:exoplayer-dash:2.17.1")
    api("com.google.android.exoplayer:exoplayer-hls:2.17.1")
    api("com.google.android.exoplayer:exoplayer-smoothstreaming:2.17.1")
//    SiliCompressor
    implementation("com.iceteck.silicompressorr:silicompressor:2.2.4")

    // Kotlin components
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")

    implementation("io.github.imablanco:zoomy:1.0.0")

    implementation("io.ak1.pix:piximagepicker:1.6.3")

    implementation ("com.airbnb.android:lottie:3.4.0")


}