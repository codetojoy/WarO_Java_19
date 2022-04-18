#!/bin/bash

set -e

CLASSPATH=lib/commons-logging-1.2.jar
CLASSPATH=$CLASSPATH:lib/spring-expression-5.2.5.RELEASE.jar
CLASSPATH=$CLASSPATH:lib/jsr305-1.3.9.jar
CLASSPATH=$CLASSPATH:lib/spring-context-5.2.5.RELEASE.jar
CLASSPATH=$CLASSPATH:lib/httpcore-4.4.12.jar
CLASSPATH=$CLASSPATH:lib/error_prone_annotations-2.1.3.jar
CLASSPATH=$CLASSPATH:lib/jackson-core-2.11.2.jar
CLASSPATH=$CLASSPATH:lib/spring-aop-5.2.5.RELEASE.jar
CLASSPATH=$CLASSPATH:lib/spring-beans-5.2.5.RELEASE.jar
CLASSPATH=$CLASSPATH:lib/commons-codec-1.11.jar
CLASSPATH=$CLASSPATH:lib/checker-compat-qual-2.0.0.jar
CLASSPATH=$CLASSPATH:lib/spring-jcl-5.2.5.RELEASE.jar
CLASSPATH=$CLASSPATH:lib/guava-24.1-jre.jar
CLASSPATH=$CLASSPATH:lib/j2objc-annotations-1.1.jar
CLASSPATH=$CLASSPATH:lib/jackson-annotations-2.11.2.jar
CLASSPATH=$CLASSPATH:lib/jackson-databind-2.11.2.jar
CLASSPATH=$CLASSPATH:lib/spring-core-5.2.5.RELEASE.jar
CLASSPATH=$CLASSPATH:lib/httpclient-4.5.10.jar
CLASSPATH=$CLASSPATH:lib/animal-sniffer-annotations-1.14.jar
CLASSPATH=$CLASSPATH:lib/commons-lang3-3.10.jar

ROOT_DIR=$PWD
SRC_DIR=$ROOT_DIR/src/main/java
TARGET_DIR=$ROOT_DIR/my_build/main
CLASSPATH=$CLASSPATH:$TARGET_DIR

# echo $CLASSPATH
java --enable-preview \
-cp $CLASSPATH \
org.peidevs.waro.Main

echo "Ready."
