import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 29
    const val compileSdk = 29
    const val versionCode = 11
    const val jvmTarget = "1.8"
    const val versionName = "1.0.0 - alpha"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    const val Anko = "0.10.8"
    const val Legacy = "1.0.0"
    const val Kotlin = "1.3.50"
    const val CoreKtx = "1.3.0"
    const val AppCompat = "1.0.2"
    const val FragmentKtx = "1.2.5"
    const val LifeCycleViewModel = "2.2.0"
    const val LifeCycleExtensions = "2.2.0"

    const val Hilt = "2.28-alpha"
    const val HiltAndroidX = "1.0.0-alpha01"

    const val Glide = "4.11.0"
    const val CardView = "1.0.0"
    const val ConstraintLayout = "1.1.3"

    const val AndroidUtils = "3.1.8"
    const val GsonConverter = "2.6.2"
    const val CrashReporter = "1.1.0"

    const val AnimatorLottie = "3.4.0"
    const val AnimatorTool = "2.1@aar"
    const val AnimatorYOYO = "2.3@aar"

    const val Paging = "2.1.0-beta01"

    const val OkHttp = "4.8.0"
    const val Retrofit = "2.9.0"
    const val RxRetrofit = "2.9.0"

    const val RxJava = "3.0.4"
    const val RxKotlin = "3.0.0"
    const val RxAndroid = "3.0.0"

    const val LoggingInterceptor = "4.8.0"
}

object Dependencies {
    object AndroidX {
        const val PagingRx = "androidx.paging:paging-rxjava2:${Versions.Paging}"
        const val PagingRuntime = "androidx.paging:paging-runtime:${Versions.Paging}"
    }

    object Network {
        const val Retrofit = "com.squareup.okhttp3:okhttp:${Versions.OkHttp}"
        const val OkHttp = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
        const val RxRetrofit = "com.squareup.retrofit2:adapter-rxjava3:${Versions.RxRetrofit}"
        const val LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.LoggingInterceptor}"
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
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
        const val Legacy = "androidx.legacy:legacy-support-core-ui:${Versions.Legacy}"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:${Versions.FragmentKtx}"
        const val LifeCycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.LifeCycleExtensions}"
        const val LifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LifeCycleViewModel}"
    }

    object Di {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Hilt}"
        const val HiltCommon = "androidx.hilt:hilt-common:${Versions.HiltAndroidX}"
        const val HiltAndroidXCompiler = "androidx.hilt:hilt-compiler:${Versions.HiltAndroidX}"
        const val HiltGoogleCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt}"
        const val HiltLifeCycle = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.HiltAndroidX}"
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
        const val GsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.GsonConverter}"
    }

    object Animator {
        const val Tool = "com.daimajia.easing:library:${Versions.AnimatorTool}"
        const val Lottie = "com.airbnb.android:lottie:${Versions.AnimatorLottie}"
        const val Yoyo = "com.daimajia.androidanimations:library:${Versions.AnimatorYOYO}"
    }
}