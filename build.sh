#!/bin/bash

mkdir input-dir
mkdir output-dir
mkdir touche-shared-task-run
args_me_corpus_zip=input-dir/args-me.zip
args_me_corpus
if test -f [!"args_me_corpus_zip"]; then
    wget https://zenodo.org/record/3274636/files/argsme.zip?download=1 -O input-dir/args-me.zip
fi
if test -f [!"args_me_corpus"]; then
    unzip input-dir/args-me.zip -d input-dir/
fi

wget https://raw.githubusercontent.com/webis-de/SIGIR-19/master/Data/topics.csv -O input-dir/topics.csv
docker build -t my-docker-image:latest -f Docker/Dockerfile .
docker run -dit --name my-docker-container -v "$(pwd)":/tmp/touche-shared-task -v "$(pwd)"/output-dir:/tmp/output-dir -v "$(pwd)"/input-dir:/tmp/input-dir -v touche-shared-task-run:/tmp/touche-shared-task-run  my-docker-image
docker exec -it my-docker-container ./tmp/touche-shared-task/run.sh
