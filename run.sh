#!/bin/sh

# Set these variables appropriate for your environment
MAVEN_LOCAL_REP=$HOME/.m2/repository
# HK2 needs JDK 1.6 unless you have Stax implementation that you can provide
#JAVA_HOME=$HOME/software/jdk1.6.0_02

HK2_VERSION=1.6.30

mkdir lib

# Copy required HK2 jars and their dependencies to lib.

cp $MAVEN_LOCAL_REP/org/glassfish/hk2/hk2-core/$HK2_VERSION/hk2-core-$HK2_VERSION.jar lib/hk2-core.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/class-model/$HK2_VERSION/class-model-$HK2_VERSION.jar lib/class-model.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/config/$HK2_VERSION/config-$HK2_VERSION.jar lib/config.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/auto-depends/$HK2_VERSION/auto-depends-$HK2_VERSION.jar lib/auto-depends.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/external/javax.inject/$HK2_VERSION/javax.inject-$HK2_VERSION.jar lib/javax.inject.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/external/asm-all-repackaged/$HK2_VERSION/asm-all-repackaged-$HK2_VERSION.jar lib/asm-all-repackaged.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/hk2-api/$HK2_VERSION/hk2-api-$HK2_VERSION.jar lib/hk2-api.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/osgi-resource-locator/1.0.1/osgi-resource-locator-1.0.1.jar lib/osgi-resource-locator.jar
cp $MAVEN_LOCAL_REP/org/jvnet/tiger-types/1.4/tiger-types-1.4.jar lib/tiger-types.jar
cp $MAVEN_LOCAL_REP/org/glassfish/hk2/external/bean-validator/$HK2_VERSION/bean-validator-$HK2_VERSION.jar lib/bean-validator.jar
cp $MAVEN_LOCAL_REP/com/googlecode/jtype/jtype/0.1.0/jtype-0.1.0.jar lib/jtype.jar

# Copy our modules to lib.
cp hello-startup/target/hello-startup-1.0-SNAPSHOT.jar ./lib/

# Uncomment if you want to debug
DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000"

# Now launch HK2
$JAVA_HOME/bin/java $DEBUG -jar lib/hello-startup-1.0-SNAPSHOT.jar

