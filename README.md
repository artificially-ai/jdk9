# jdk9

This repository contains some sample code I built against the JDK 9 EA realease build 140.

# Build

Download the JDK 9 EA here -> [JDK9](https://jdk9.java.net/download/)

After cloning the repository execute the following command:

```
./gradlew clean build shadowJar
```

# Running the application

Currently there are two main applications within the code, but only one is configured via Gradle. In order to run
the second application you can change the ```build.gradle``` file or just run it from IntelliJ IDEA 2016.2.

## With Gradle

To run with Gradle, execute the following command:

```
java -jar build/libs/jdk9-1.0-SNAPSHOT-all.jar -buffer 5 -subs 30
```

### Buffer

The ```-buffer``` parameter means the amount of notifications you want to receive. If not informed, it will be defaulted to 5.

### Subs

The ```-subs``` parameter means the amount of Subscribers you want to listen to the Publisher. If not informed, it will be
defaulted to 10.
