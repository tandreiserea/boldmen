export UPSTREAM_URL=http://localhost:7888
java -Dserver.port=6888 -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar target/boldmen-envoy-0.0.1-SNAPSHOT.jar

