#!/bin/sh
mvn clean install
./docker-build-envoy.sh
./docker-build-client.sh
./docker-build-proxy.sh
