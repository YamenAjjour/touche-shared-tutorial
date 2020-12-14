#!/bin/bash

docker build -t my-docker-image:latest -f Docker/Dockerfile .
docker run --rm -ti --name my-docker-container -v "$1":/tmp/input-dir -v "$2":/tmp/output-dir my-docker-image

