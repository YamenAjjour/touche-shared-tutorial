FROM openjdk:8

RUN mkdir /tmp/touche-shared-task-run/
COPY build/libs/touche-shared-task-1.0-SNAPSHOT-all.jar /tmp/touche-shared-task/
RUN java -jar /tmp/touche-shared-task/touche-shared-task-1.0-SNAPSHOT-all.jar Main /tmp/input-dir/ /tmp/output-dir/