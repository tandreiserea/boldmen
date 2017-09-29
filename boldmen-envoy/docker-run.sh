#!/bin/sh

docker run -itd --rm --env-file "./http-client.env" --name boldmen-envoy -p 10000:10000 -v "$(pwd)/conf":/etc/envoy boldmen/boldmen-envoy:latest

docker run -itd --rm --env-file "./http-client.env" --name boldmen-client -p 6888:6888 -v "$(pwd)/conf":/etc/envoy boldmen/boldmen-envoy-client:latest

docker run -itd --rm --env-file "./http-client.env" --name boldmen-proxy -p 5888:5888 -v "$(pwd)/conf":/etc/envoy boldmen/boldmen-envoy-client:latest