plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("name.remal.check-dependency-updates") version "1.1.6"
}

android {
    compileSdkVersion(Application.compileSdk)
    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "v$versionName($versionCode)")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    packagingOptions {
        exclude("META-INF/library_release.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    fun def(vararg dependencies: String) {
        for (dependency in dependencies) implementation(dependency)
    }

    def(
        Dependencies.Essential.Anko,
        Dependencies.Essential.CoreKtx,
        Dependencies.Essential.Kotlin,

        Dependencies.Network.Jsoup,
        Dependencies.Network.OkHttp,
        Dependencies.Network.Retrofit,

        Dependencies.Rx.RxRetrofit,
        Dependencies.Rx.Kotlin,
        Dependencies.Rx.Android,

        Dependencies.Di.Hilt,

        Dependencies.Ui.Glide,
        Dependencies.Ui.CardView,
        Dependencies.Ui.Lottie,
        Dependencies.Ui.SuperBottomSheet,
        Dependencies.Ui.ConstraintLayout,

        Dependencies.Util.AndroidUtil,
        Dependencies.Util.CrashReporter
    )

    kapt(Dependencies.Di.HiltCompiler)
    kapt(Dependencies.Ui.GlideCompiler)
}
