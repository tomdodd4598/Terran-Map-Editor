plugins {
    kotlin("multiplatform") version "1.8.+"
    id("org.jetbrains.compose") version "1.4.+"
}

group = "com.dodd"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting {

        }
    }
}

compose.desktop {
    application {
        mainClass = "dodd.MainKt"
        nativeDistributions {
            //targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "TerranMapEditor"
            packageVersion = "1.0.0"
        }
    }
}
