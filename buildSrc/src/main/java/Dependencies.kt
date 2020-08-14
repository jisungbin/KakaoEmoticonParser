import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 29
    const val compileSdk = 29
    const val versionCode = 13
    const val jvmTarget = "1.8"
    const val versionName = "1.0.2 - alpha"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    const val Anko = "0.10.8"
    const val Kotlin = "1.3.72"
    const val CoreKtx = "1.3.0"

    const val Hilt = "2.28-alpha"

    const val Glide = "4.11.0"
    const val CardView = "1.0.0"
    const val ConstraintLayout = "1.1.3"

    const val AndroidUtils = "3.1.8"
    const val CrashReporter = "1.1.0"

    const val AnimatorLottie = "3.4.0"

    const val Jsoup = "1.12.1"
    const val OkHttp = "4.8.0"
    const val Retrofit = "2.9.0"
    const val RxJava = "3.0.4"

    const val RxKotlin = "3.0.0"
    const val RxAndroid = "3.0.0"
    const val RxRetrofit = "2.9.0"
}

object Dependencies {

    object Network {
        const val Jsoup = "org.jsoup:jsoup:${Versions.Jsoup}"
        const val Retrofit = "com.squareup.okhttp3:okhttp:${Versions.OkHttp}"
        const val OkHttp = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
        const val RxRetrofit = "com.squareup.retrofit2:adapter-rxjava3:${Versions.RxRetrofit}"
    }

    object Rx {
        const val Java = "io.reactivex.rxjava3:rxjava:${Versions.RxJava}"
        const val Kotlin = "io.reactivex.rxjava3:rxkotlin:${Versions.RxKotlin}"
        const val Android = "io.reactivex.rxjava3:rxandroid:${Versions.RxAndroid}"
    }

    object Essential {
        const val Anko = "org.jetbrains.anko:anko:${Versions.Anko}"
        const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin}"
    }

    object Di {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Hilt}"
        const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt}"
    }

    object Ui {
        const val Glide = "com.github.bumptech.glide:glide:${Versions.Glide}"
        const val CardView = "androidx.cardview:cardview:${Versions.CardView}"
        const val GlideCompiler = "com.github.bumptech.glide:compiler:${Versions.Glide}"
        const val ConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
    }

    object Utils {
        const val AndroidUtils = "com.github.sungbin5304:AndroidUtils:${Versions.AndroidUtils}"
        const val CrashReporter = "com.balsikandar.android:crashreporter:${Versions.CrashReporter}"
    }

    object Animator {
        const val Lottie = "com.airbnb.android:lottie:${Versions.AnimatorLottie}"
    }
}