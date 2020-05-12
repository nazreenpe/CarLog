#!/usr/bin/env bash

pushd frontend
npm run-script build
popd
./gradlew clean build -x test
heroku jar:deploy build/libs/carLog-*.jar
