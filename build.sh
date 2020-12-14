#!/bin/bash

input_dir=~/tmp/input-dir
output_dir=~/tmp/output-dir
mkdir "$input_dir"
mkdir "$output_dir"
args_me_corpus_zip="$input_dir/args-me.zip"
args_me_corpus="$input_dir/args-me-1.0-cleaned.json"
topics_zip="$input_dir/topics-task-1.zip"

if [ ! -e "$args_me_corpus_zip" ]
then
echo "$args_me_corpus_zip"
    wget  https://zenodo.org/record/4139439/files/argsme-1.0-cleaned.zip?download=1 -O "$args_me_corpus_zip"
fi

if [ ! -e "$args_me_corpus" ]
then
    unzip "$args_me_corpus_zip" -d "$input_dir"
fi

if [ ! -e "$topics_zip" ]
then
	wget https://webis.de/events/touche-21/topics-task-1.zip -O "$topics_zip"
	unzip "$topics_zip" -d "$input_dir"
fi


docker build -t my-docker-image:latest -f Docker/Dockerfile .
docker run --rm -ti --name my-docker-container -v "$input_dir":/tmp/input-dir -v "$output_dir":/tmp/output-dir my-docker-image

