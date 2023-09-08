import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.gms.google-services")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.movies2app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movies2app"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Navigation
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.24.10-beta")
    implementation ("androidx.navigation:navigation-compose:2.6.0")
    implementation ("androidx.compose.animation:animation:1.4.3")
    //Material to draw
    implementation ("androidx.compose.material:material-icons-extended:1.5.0")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha05")
    implementation ("androidx.compose.material3:material3-window-size-class:1.1.1")
    //noinspection BomWithoutPlatform
    implementation ("androidx.compose:compose-bom:2023.06.01")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //viewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // livedata
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.3")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // room database
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.5.2")

    //dagger hilt
    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt ("com.google.dagger:hilt-compiler:2.46.1")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    //okHttp
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation ("com.google.firebase:firebase-analytics")
    //firebase auth
    implementation ("com.google.firebase:firebase-auth")
    //firebase realdatabase
    implementation ("com.google.firebase:firebase-database")
    //firebase storage
    implementation ("com.google.firebase:firebase-storage")
    //styleToast
    implementation ("io.github.muddz:styleabletoast:2.4.0")
    //paging
    implementation ("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation ("androidx.paging:paging-common-ktx:3.1.1")
    implementation ("androidx.paging:paging-compose:1.0.0-alpha16")

    // coil
    implementation ("io.coil-kt:coil-compose:2.2.1")
    implementation ("io.coil-kt:coil-gif:2.1.0")

    //sdp
    implementation ("com.github.Kaaveh:sdp-compose:1.1.0")



}