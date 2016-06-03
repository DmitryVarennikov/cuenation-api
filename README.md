CueNation API
=============

![Build status](https://travis-ci.org/dVaffection/cuenation-api.svg?branch=master)

Provides API ends for [CueNation Chrome ext](https://github.com/dVaffection/cuenation-chrome-ext)

[Wiki documentation](https://github.com/dVaffection/cuenation-api/wiki)

## How to test

`./gradlew test`

## How to run

`./gradlew run`

## How to deploy

### Build jar
`./gradlew build` --> `build/libs/cuenation-api-<version>.jar`

### Run jar

* Remove current symlink `rm build/libs/cuenation-api.jar`
* Create a new symlink for the latest build `ln -s build/libs/cuenation-api-<version>.jar build/libs/cuenation-api.jar`
* Kill current process `kill <pid>`
* Start a new process `/usr/bin/java -jar build/libs/cuenation-api.jar &> /dev/null &`