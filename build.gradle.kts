plugins {
//    java
    application
}

group = "com.movietone"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.derby:derby:10.17.1.0")
}

application {
    mainClass.set("com.movietone.Apriori")
}