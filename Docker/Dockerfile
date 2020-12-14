FROM openjdk:8
RUN mkdir /tmp/input-dir
RUN mkdir /tmp/output-dir
COPY . "/tmp/touche-shared-task"
ENTRYPOINT /tmp/touche-shared-task/run.sh /tmp/input-dir /tmp/output-dir


