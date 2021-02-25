plugins {
    java
    application
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "CompactStringBug"
//    applicationDefaultJvmArgs = listOf("-Djava.compiler=NONE")
//    applicationDefaultJvmArgs = listOf("-XX:-CompactStrings")
}

dependencies {
    implementation("commons-lang", "commons-lang", "2.6")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
