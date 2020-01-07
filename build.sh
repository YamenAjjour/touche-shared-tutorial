#!/bin/bash


mkdir input-dir
mkdir output-dir
mkdir touche-shared-task-run
%wget https://zenodo.org/record/3274636/files/argsme.zip?download=1 -O input-dir/args-me.zip
%unzip input-dir/args-me.zip -d input-dir/
wget https://raw.githubusercontent.com/webis-de/SIGIR-19/master/Data/topics.csv -O input-dir/topics.csv
docker build -t my-docker -f Docker/Dockerfile .
docker run -v "$(pwd)":/tmp/touche-shared-task -v "$(pwd)"/output-dir:/tmp/output-dir -v "$(pwd)"/input-dir:/tmp/input-dir -v touche-shared-task-run:/tmp/touche-shared-task-run -d -t my-docker:latest
docker exec -it my-docker ./tmp/touche-shared-task/run.sh