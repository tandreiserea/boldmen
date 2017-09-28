#!/bin/sh

docker run -itd --rm --env-file "./http-client.env" --name client -p 10000:10000 -v "$(pwd)/conf":/etc/envoy boldmen/boldmen-envoy-client:latest