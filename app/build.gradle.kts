plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //ksp
    id("com.google.devtools.ksp")
    //hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sohezsoft.student"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sohezsoft.student"
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
    buildFeatures{
        viewBinding = true
        dataBinding = true
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.preference:preference-ktx:1.2.1")
    //navigation control
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Glide - url image load
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //Retrofit - Network Request && RetrofitConveter - Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    //livedata
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    //JSON to MODEL Class Convert
    implementation ("com.google.code.gson:gson:2.10.1")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Lottie animation
    implementation ("com.airbnb.android:lottie:3.4.0")

    // Room components
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    //KSP is - (New Fast)annotation Processor it is compiler plugin,
    //Generate Code at Compile Time, base ON annotation.
    ksp("androidx.room:room-compiler:2.6.0")

    //Dependency Injection - Hilt (current Not use)
    implementation("com.google.dagger:hilt-android:2.44")
    ksp("com.google.dagger:hilt-android-compiler:2.44")

    //DataBinding
    implementation("androidx.databinding:databinding-runtime:8.1.2")

    //Circle ImageView
    implementation("com.mikhaellopez:circularimageview:4.3.1")

    //swip to refresh
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //custom calander
    implementation("com.kizitonwose.calendar:view:2.4.0")

}

