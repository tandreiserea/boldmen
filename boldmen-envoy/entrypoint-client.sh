#!/usr/bin/env bash

# start client
java -DuseCache=true -Denvoy=true -DUPSTREAM_URL=http://10.130.24.107:5888 -DUPSTREAM_URL_ENVOY=http://10.130.24.107:15001 -Dsun.net.http.allowRestrictedHeaders=true  -Dserver.port=6888 -jar /deployments/boldmen-envoy-0.0.1-SNAPSHOT.jar

