plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.theRouter) apply false
    kotlin("plugin.serialization") version "1.5.0" apply false
    id("maven-publish")
}


// 版本号计算
fun calculateVersionCode(vn: String): Int {
    val splits = vn.split("\\.".toRegex()).toTypedArray()
    println("分割VersionName:${splits.contentToString()}")
    val w = splits[0].toInt() * 10000
    val b = splits[1].toInt() * 100
    val g = splits[2].toInt()
    if (b > 9900 || g > 99) {
        throw Exception("版本号不符合规范: eg: 1.99.99 后两位必须为99以内数。")
    }
    return w + b + g
}

ext {
    // 使用set重写上面的
    set("compileSdk", 35)
    set("minSdk", 26)
    set("targetSdk", 35)
    set("javaVersion", "17")
    set("versionName", "5.8.0")
    set("versionCode", calculateVersionCode((get("versionName") ?: "1.0.0").toString()))
}