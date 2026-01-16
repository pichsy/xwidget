plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("maven-publish")
}


android {
    namespace = "com.pichs.xwidget"
    compileSdk = rootProject.ext.get("compileSdk") as Int

    defaultConfig {
        minSdk = rootProject.ext.get("minSdk") as Int
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(rootProject.ext.get("javaVersion") as String)
        targetCompatibility = JavaVersion.toVersion(rootProject.ext.get("javaVersion") as String)
    }

    kotlinOptions {
        jvmTarget = rootProject.ext.get("javaVersion") as String
    }


    buildFeatures {
        viewBinding = true
    }
}



dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.androidx.recyclerview)
    api(libs.androidx.annotation)
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.coroutines.core)
    api(libs.androidx.activity.ktx)
    api(libs.androidx.fragment.ktx)

//    api(libs.okhttp)
//    api(libs.retrofit)
//    api(libs.loggingInterceptor)
//    api(libs.gson)
//    api(libs.converterGson)
//    api(libs.okio)

//    api(libs.androidx.room.ktx)
//    api(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)
}



tasks.register<Javadoc>("javadoc") {
    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).charSet = "UTF-8"
    // 只处理 Java 源码，避免 Kotlin 导致 path 为空
    val javaSrc = fileTree("src/main/java") { include("**/*.java") }
    source = javaSrc
    classpath += files(android.bootClasspath.joinToString(File.pathSeparator))
    (options as StandardJavadocDocletOptions).links("http://docs.oracle.com/javase/7/docs/api/")
    (options as StandardJavadocDocletOptions).linksOffline(
        "http://d.android.com/reference",
        "${android.sdkDirectory}/docs/reference"
    )
    exclude("**/BuildConfig.java")
    exclude("**/R.java")
    isFailOnError = false
}

tasks.withType<Javadoc> {
    enabled = false
}

tasks.register<Jar>("androidJavadocsJar") {
    dependsOn("javadoc")
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc").get().outputs.files)
}

// 此写法可忽略文件夹层级带来的影响
apply(from = "${rootProject.rootDir}/maven.gradle")