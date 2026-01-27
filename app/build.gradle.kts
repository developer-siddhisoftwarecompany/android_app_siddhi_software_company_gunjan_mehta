plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.example.gunjan_siddhisoftwarecompany"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gunjan_siddhisoftwarecompany"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = false
        unitTests.all {
            it.enabled = false
        }
        sourceSets {
            getByName("androidTest").java.srcDirs("src/androidTestDisabled/java")
        }
    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.firebase.crashlytics)
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.exifinterface:exifinterface:1.3.7")
    implementation("org.osmdroid:osmdroid-android:6.1.18")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    val camerax_version ="1.3.0"
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("androidx.camera:camera-extensions:${camerax_version}")
    implementation("androidx.preference:preference:1.2.1")

    implementation("com.github.skydoves:colorpickerview:2.2.4")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation(libs.ads.mobile.sdk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


}