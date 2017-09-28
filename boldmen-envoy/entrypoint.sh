#!/usr/bin/env bash

# start envoy
/usr/local/bin/envoy -c /etc/envoy/envoy.json &

# start client
java -jar /deployments/boldmen-envoy-0.0.1-SNAPSHOT.jar
