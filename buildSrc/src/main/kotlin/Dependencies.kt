import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val versionCode = 16
    const val jvmTarget = "1.8"
    const val versionName = "1.2.0"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    object Essential {
        const val Kotlin = "1.4.31"
        const val Gradle = "7.0.0-alpha09"
        const val CoreKtx = "1.3.2"
    }

    object Di {
        const val Hilt = "2.28-alpha"
    }

    object Ui {
        const val Glide = "4.12.0"
        const val CardView = "1.0.0"
        const val SuperBottomSheet = "2.0.0"
        const val ConstraintLayout = "2.0.4"
        const val Flexbox = "2.0.1"
        const val Lottie = "3.6.1"
    }

    object Util {
        const val AndroidUtil = "4.2.6"
        const val CrashReporter = "1.1.0"
        const val JsonConverter = "2.4.0"
    }

    object Network {
        const val Jsoup = "1.13.1"
        const val OkHttp = "4.9.1"
        const val Retrofit = "2.9.0"
    }

    object Rx {
        const val RxKotlin = "3.0.1"
        const val RxAndroid = "3.0.0"
        const val RxRetrofit = "2.9.0"
    }
}

object Dependencies {
    val network = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
    )

    val rx = listOf(
        "io.reactivex.rxjava3:rxkotlin:${Versions.Rx.RxKotlin}",
        "io.reactivex.rxjava3:rxandroid:${Versions.Rx.RxAndroid}",
        "com.squareup.retrofit2:adapter-rxjava3:${Versions.Rx.RxRetrofit}"
    )

    val essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}"
    )

    val di = listOf(
        "com.google.dagger:hilt-android:${Versions.Di.Hilt}"
    )

    val ui = listOf(
        "com.airbnb.android:lottie:${Versions.Ui.Lottie}",
        "com.github.bumptech.glide:glide:${Versions.Ui.Glide}",
        "androidx.cardview:cardview:${Versions.Ui.CardView}",
        "com.github.andrefrsousa:SuperBottomSheet:${Versions.Ui.SuperBottomSheet}",
        "androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}",
        "com.google.android:flexbox:${Versions.Ui.Flexbox}"
    )

    val util = listOf(
        "com.github.sungbin5304:AndroidUtils:${Versions.Util.AndroidUtil}",
        "com.balsikandar.android:crashreporter:${Versions.Util.CrashReporter}",
        "com.squareup.retrofit2:converter-gson:${Versions.Util.JsonConverter}"
    )

    val compiler = listOf(
        "com.google.dagger:hilt-android-compiler:${Versions.Di.Hilt}",
        "com.github.bumptech.glide:compiler:${Versions.Ui.Glide}"
    )
}
