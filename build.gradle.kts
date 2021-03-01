plugins {
    java
    application
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "CompactStringBug"

// Enabling any of the below JVM args works around the bug

//    applicationDefaultJvmArgs = listOf("-Xint")
//    applicationDefaultJvmArgs = listOf("-XX:TieredStopAtLevel=1")
//    applicationDefaultJvmArgs = listOf("-Djava.compiler=NONE")
//    applicationDefaultJvmArgs = listOf("-XX:-CompactStrings")
}

dependencies {
    implementation("commons-lang", "commons-lang", "2.6")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
