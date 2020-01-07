FROM openjdk:8

RUN mkdir /tmp/touche-shared-task-run/
RUN ./tmp/touche-shared-task/gradlew build --info
RUN java -jar /tmp/touche-shared-task/lib/build/touche-shared-task-1.0-SNAPSHOT-all.jar Main /tmp/input-dir/ /tmp/output-dir/