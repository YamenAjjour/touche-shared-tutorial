#!/bin/bash

mkdir input-dir
mkdir output-dir
docker build -t my-docker-image:latest -f Docker/Dockerfile .
docker run -dit --name my-docker-container -v "$(pwd)":/tmp/touche-shared-task -v "$1":/tmp/input-dir -v "$2":/tmp/output-dir my-docker-image
docker exec -it my-docker-container bash ./tmp/touche-shared-task/run.sh /tmp/input-dir /tmp/output-dir
