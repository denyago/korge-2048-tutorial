import com.soywiz.korge.gradle.*

buildscript {
  val korgePluginVersion: String by project

  repositories {
    mavenLocal()
    mavenCentral()
    google()
    maven { url = uri("https://plugins.gradle.org/m2/") }
  }
  dependencies {
    classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
  }
}

plugins {
  id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

apply<KorgeGradlePlugin>()

korge {
  id = "name.denyago.game2048"

  targetJvm()
  targetJs()
  // targetDesktop()
  // targetIos()
  // targetAndroidIndirect() // targetAndroidDirect()
}