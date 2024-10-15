plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "kodi.tv.iptv.m3u.smarttv"
    compileSdk = 34

    defaultConfig {
        applicationId = "kodi.tv.iptv.m3u.smarttv"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    flavorDimensions += listOf("firebase")
    productFlavors {
        create("dev") {
            dimension = "firebase"
            applicationId = "kodi.tv.iptv.m3u.smarttv.dev"

            buildConfigField("String","BANNER_ADD_ID","\"" + "ca-app-pub-3940256099942544/6300978111"+ "\"")
            buildConfigField("String","INTERSTITIAL_ADD_ID","\"" + "ca-app-pub-3940256099942544/1033173712"+ "\"")
            buildConfigField("String","FB_BANNER_ADD_ID","\"" + "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"+ "\"")
            buildConfigField("String","FB_INTERSTITIAL_ADD_ID","\"" + "VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID"+ "\"")
            buildConfigField("String","ADMOB_NATIVE_ID","\"" + "ca-app-pub-6013605396446078/3857563619"+ "\"")


        }
        create("pro") {
            dimension = "firebase"
            applicationId = "kodi.tv.iptv.m3u.smarttv"

            /*buildConfigField("String","ADMOB_NATIVE_ID","\"" + "ca-app-pub-3940256099942544/2247696110"+ "\"")
            buildConfigField("String","BANNER_ADD_ID","\"" + "ca-app-pub-3940256099942544/6300978111"+ "\"")
            buildConfigField("String","INTERSTITIAL_ADD_ID","\"" + "ca-app-pub-3940256099942544/1033173712"+ "\"")
            buildConfigField("String","FB_BANNER_ADD_ID","\"" + "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"+ "\"")
            buildConfigField("String","FB_INTERSTITIAL_ADD_ID","\"" + "VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID"+ "\"")
           */

            buildConfigField("String","ADMOB_NATIVE_ID","\"" + "ca-app-pub-6013605396446078/3857563619"+ "\"")
            buildConfigField("String","BANNER_ADD_ID","\"" + "ca-app-pub-6013605396446078/3573674349"+ "\"")
            buildConfigField("String","INTERSTITIAL_ADD_ID","\"" + "ca-app-pub-6013605396446078/3756920444"+ "\"")
            buildConfigField("String","FB_BANNER_ADD_ID","\"" + "308871724906304_321673193626157"+ "\"")
            buildConfigField("String","FB_INTERSTITIAL_ADD_ID","\"" + "308871724906304_308873668239443"+ "\"")

        }
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class-android:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    implementation ("androidx.compose.material:material:1.6.6")

    // Lifecycle
    val lifecycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.6")

    //coil rememberimage
    implementation ("io.coil-kt:coil-compose:1.3.2")
    //lottie
    implementation( "com.airbnb.android:lottie-compose:5.2.0")
    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.2")
    //pager
    implementation ("androidx.compose.foundation:foundation:1.6.4")
    //firebase
    implementation (platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-crashlytics")

    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("androidx.media3:media3-exoplayer:1.3.0")
    implementation ("androidx.media3:media3-ui:1.3.0")
    implementation("androidx.media3:media3-exoplayer-hls:1.3.0")

    implementation ("com.google.android.gms:play-services-ads:23.4.0")
    implementation ("com.facebook.android:audience-network-sdk:6.16.0")
    implementation("com.facebook.infer.annotation:infer-annotation:0.18.0")

}