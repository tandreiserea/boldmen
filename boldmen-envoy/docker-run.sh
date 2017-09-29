#!/bin/sh

docker run -itd --rm --name boldmen-envoy -p 15001:15001 boldmen/boldmen-envoy:latest

docker run -itd --rm --name boldmen-client -p 6888:6888 -v "$(pwd)/conf":/etc/envoy boldmen/boldmen-envoy-client:latest

docker run -itd --rm --name boldmen-proxy -p 5888:5888 -v "$(pwd)/conf":/etc/envoy boldmen/boldmen-envoy-proxy:latest