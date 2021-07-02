# EU Digital COVID Certificate - Certlogic Android *light*

<p align="center">
  <a href="/../../commits/" title="Last Commit">
    <img src="https://img.shields.io/github/last-commit/eu-digital-green-certificates/dgc-certlogic-android?style=flat">
  </a>
  <a href="/../../issues" title="Open Issues">
    <img src="https://img.shields.io/github/issues/eu-digital-green-certificates/dgc-certlogic-android?style=flat">
  </a>
  <a href="./LICENSE" title="License">
    <img src="https://img.shields.io/badge/License-Apache%202.0-green.svg?style=flat">
  </a>
  <a href="https://api.reuse.software/info/github.com/corona-warn-app/dgc-certlogic-android" title="REUSE Status"><img src="https://api.reuse.software/badge/github.com/corona-warn-app/dgc-certlogic-android"></a>      
</p>

<p align="center">
  <a href="#about">About</a> •
  <a href="#building">Building</a> •
  <a href="#how-to-contribute">Contribute</a> •
  <a href="#contributors">Contributors</a> •
  <a href="#licensing">Licensing</a>
</p>

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
## How to contribute  

Contribution and feedback is encouraged and always welcome. For more information about how to contribute, the project structure, 
as well as additional contribution information, see our [Contribution Guidelines](./CONTRIBUTING.md). By participating in this 
project, you agree to abide by its [Code of Conduct](./CODE_OF_CONDUCT.md) at all times.

## Contributors  

Our commitment to open source means that we are enabling - in fact encouraging - all interested parties to contribute and become part of its developer community.

## Licensing

Copyright (C) 2021 T-Systems International GmbH, SAP SE and all other contributors

Licensed under the **Apache License, Version 2.0** (the "License"); you may not use this file except in compliance with the License.

You may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" 
BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the [LICENSE](./LICENSE) for the specific 
language governing permissions and limitations under the License.

