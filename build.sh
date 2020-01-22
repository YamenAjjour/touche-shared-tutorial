#!/bin/bash

mkdir input-dir
mkdir output-dir
args_me_corpus_zip=input-dir/args-me.zip
args_me_corpus=input-dir/args-me.json
if [ ! -e "$args_me_corpus_zip" ]
then
    wget  https://zenodo.org/record/2839112/files/args-me.zip?download=1 -O input-dir/args-me.zip
fi

if [ ! -e "$args_me_corpus" ]
then
    unzip input-dir/args-me.zip -d input-dir/
fi

cp /media/training-datasets/ir-lecture-task-1/touche-2020-first-subtask-2019-11-23/topics.xml input-dir/
docker build -t my-docker-image:latest -f Docker/Dockerfile .
docker run -dit --name my-docker-container -v "$(pwd)":/tmp/touche-shared-task -v input-dir:/tmp/input-dir -v output-dir:/tmp/output-dir my-docker-image
docker exec -it my-docker-container bash ./tmp/touche-shared-task/run.sh input-dir output-dir
