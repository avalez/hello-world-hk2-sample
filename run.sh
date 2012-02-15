#!/bin/sh

# Set these variables appropriate for your environment
MAVEN_LOCAL_REP=$HOME/.m2/repository
# HK2 needs JDK 1.6 unless you have Stax implementation that you can provide
#JAVA_HOME=$HOME/software/jdk1.6.0_02

HK2_VERSION=1.6.30

mkdir lib
# Copy required HK2 jars and their dependencies to lib.

cp $MAVEN_LOCAL_REP/org/glassfish/hk2/auto-depends/$HK2_VERSION/auto-depends-$HK2_VERSION.jar lib/auto-depends.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/hk2-api/$HK2_VERSION/hk2-api-$HK2_VERSION.jar lib/hk2-api.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/config/$HK2_VERSION/config-$HK2_VERSION.jar lib/config.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/hk2-core/$HK2_VERSION/hk2-core-$HK2_VERSION.jar lib/hk2-core.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/hk2/$HK2_VERSION/hk2-$HK2_VERSION.jar lib/hk2.jar
cp $MAVEN_LOCAL_REP/org/jvnet/tiger-types/1.4/tiger-types-1.4.jar lib/tiger-types.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/external/javax.inject/$HK2_VERSION/javax.inject-$HK2_VERSION.jar lib/javax.inject.jar
# Copy our modules to lib.
cp hello-*/target/*.jar ./lib

#Now launch HK2
$JAVA_HOME/bin/java -jar lib/hk2.jar com.sun.enterprise.module.bootstrap.Main

