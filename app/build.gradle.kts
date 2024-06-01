plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.bcake"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.bcake"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //Gif
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation ("com.google.android.gms:play-services-auth:21.0.0")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.github.bumptech.glide:compiler:4.12.0")
    //Map Service
    implementation ("com.google.maps:google-maps-services:0.15.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    //Image badge
    implementation ("io.github.nikartm:image-support:2.0.0")
    //Retrofit
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:retrofit:2.1.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.1.0")
    //Picasso
    implementation ("com.squareup.picasso:picasso:2.8")
    //Facebook
    implementation ("com.facebook.android:facebook-login:latest.release")
    //CircleImage
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //Zalo
    implementation(files("C:\\DATN\\New folder\\zpdk-release-v3.1.aar"))
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-base:18.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}