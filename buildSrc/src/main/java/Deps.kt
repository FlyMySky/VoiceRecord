object Androids {
    const val compileSdkVersion = 28
    const val minSdkVersion = 15
    const val targetSdkVersion = 28
    const val applicationId = "com.skwen.voicerecord"
    const val applicationIdVoice = "com.skwen.voicerecord.voicelib"
    const val versionCode = 1
    const val versionName = "1.0"
}

private object Versions {
    const val kotlinVersion = "1.2.71"
    const val toolsBuildGradleVersion = "3.2.1"
    const val greenDaoGradlePluginVersion = "3.2.2"
    const val appcompatVersion = "28.0.0"
    const val constraintLayoutVersion = "1.1.3"
    const val junitVersion = "4.12"
    const val runnerVersion = "1.0.2"
    const val espressoVersion = "3.0.2"

    //third
    const val recyclerviewSwipeVersion = "1.2.0"
    const val statusbarVersion = "1.4.0"
    const val roomVersion = "1.1.1"
    const val greenDaoVersion = "3.2.2"
    const val rxJavaVersion = "2.2.4"
    const val rxAndroidVersion = "2.1.0"
}


object Deps {
    val builds = Builds
    val tests = Tests
    val supports = Supports
    val thirds = Thirds
}

object Builds {
    const val toolsBuildGradle = "com.android.tools.build:gradle:${Versions.toolsBuildGradleVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val greenDaoGradlePlugin = "org.greenrobot:greendao-gradle-plugin:${Versions.greenDaoGradlePluginVersion}"
}

object Tests {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val runner = "com.android.support.test:runner:${Versions.runnerVersion}"
    const val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espressoVersion}"
}

object Supports {
    const val kotlinSupport = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val appcompatV7 = "com.android.support:appcompat-v7:${Versions.appcompatVersion}"
    const val design = "com.android.support:design:${Versions.appcompatVersion}"
    const val cardview = "com.android.support:cardview-v7:${Versions.appcompatVersion}"
    const val recyclerview = "com.android.support:recyclerview-v7:${Versions.appcompatVersion}"
    const val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayoutVersion}"
}

object Thirds {
    const val recyclerviewSwipe = "com.yanzhenjie:recyclerview-swipe:${Versions.recyclerviewSwipeVersion}"
    const val statusBarUtil = "com.jaeger.statusbarutil:library:${Versions.statusbarVersion}"//沉浸式状态栏
    /**
     * room
     */
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"//annotationProcessor
    const val roomRxjava2 = "androidx.room:room-rxjava2:${Versions.roomVersion}"
    const val roomGuava = "androidx.room:room-guava:${Versions.roomVersion}"
    const val roomCoroutines = "androidx.room:room-coroutines:${Versions.roomVersion}"
    /**
     * greenDao
     */
    const val greenDao = "org.greenrobot:greendao:${Versions.greenDaoVersion}"
    /**
     * rxjava
     */
    const val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}"
    const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
}