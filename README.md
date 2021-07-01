# EU Digital COVID Certificate - Certlogic Android *light*

## About
This repo is a mod of the [upstream repo](https://github.com/corona-warn-app/dgc-certlogic-android).
It produces a lightweight dependency for the [CWA](https://github.com/corona-warn-app/cwa-app-android) to use.

## Building
* Run `./gradlew clean buildAllArtifacts`
* Copy the folder `local-maven-repo` under `<root>/build/` into your project
* In your main project, wire up the local maven repo and include the dependency in your module, e.g.:

```groovy
repositories {
    maven { url "../local-maven-repo" } // If `local-maven-repo` is in your projects root folder
}

dependencies {
    implementation 'dgca.verifier.app:dgc-certlogic-android-light:+'
    // ...
}
```
