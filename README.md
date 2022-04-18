
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

* requires JDK 19.ea.5.lm-open via SDKMan! 

useful commands:

* `./clean.sh`
* `./compile.sh`
* `./test.sh`

To Run:
---------

* configure `src/main/java/org/peidevs/waro/config/Config.java`
* `./run.sh`

Rules:
---------

Rules are [here](Rules.md).
