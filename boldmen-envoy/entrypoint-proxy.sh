#!/usr/bin/env bash

# start client
java -DuseCache=false -DUPSTREAM_URL=http://10.130.24.107:7888 -Dsun.net.http.allowRestrictedHeaders=true -Dserver.port=5888 -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar /deployments/boldmen-envoy-0.0.1-SNAPSHOT.jar

