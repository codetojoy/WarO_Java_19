
WarO_Java
=========

a Java submission for War-O as a code exercise

* this project uses:
    - Java 8 streams
    - Java 10 `var`
    - Java 15 (second preview) `record`
    - Java 15 (preview) `sealed`
    - Java 19 (preview) virtual threads
* goals include: 
    - API/remote strategy (called with virtual threads)
        - see [here](https://github.com/codetojoy/WarO_Strategy_API_Java) for implementation
    - functional style
    - immutable objects (not necessarily efficient)
    - minimal use of for-loops
* Spring's Java configuration is used to configure players
* jars assumed to be in `~/lib` (not included in repo)

To Build:
---------

* tested with JDK `19.0.1-oracle` via [SDKMan!](https://sdkman.io/)

useful commands:

* `sdk env`
* `./gradlew clean`
* `./gradlew test`
* `./gradlew testIntegration`
    - requires API/remote Strategy server to be running (see above)

To Run:
---------

* configure `src/main/java/org/peidevs/waro/config/Config.java`
* `./gradlew run`

Rules:
---------

Rules are [here](Rules.md).
