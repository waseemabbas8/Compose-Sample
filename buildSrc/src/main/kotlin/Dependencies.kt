object Dependencies {
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompose}"

    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"

    const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val ktxDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.ktxDateTime}"
    const val ktxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.ktxSerialization}"


    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val android = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val resources = "io.ktor:ktor-client-resources:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val content = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    }


}